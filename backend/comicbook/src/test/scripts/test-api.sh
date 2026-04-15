#!/usr/bin/env bash
# ---------------------------------------------------------------------------
# test-api.sh — smoke test for the Comicbook REST API
# Usage: ./test-api.sh [BASE_URL]
#   BASE_URL defaults to http://localhost:8080
# Compatible with macOS (BSD) and Linux.
# ---------------------------------------------------------------------------
set -euo pipefail

BASE_URL="${1:-http://localhost:8080}"
API="$BASE_URL/api/v1/comicbooks"

# ── colours ────────────────────────────────────────────────────────────────
RED='\033[0;31m'; GREEN='\033[0;32m'; YELLOW='\033[1;33m'
CYAN='\033[0;36m'; BOLD='\033[1m'; RESET='\033[0m'

# ── helpers ────────────────────────────────────────────────────────────────
pretty() {
  if command -v jq &>/dev/null; then jq '.'; else python3 -m json.tool 2>/dev/null || cat; fi
  return 0
}

section() {
  local title="$1"
  echo -e "\n${CYAN}${BOLD}▶ ${title}${RESET}"
  return 0
}

ok() {
  local msg="$1"
  echo -e "  ${GREEN}✔ ${msg}${RESET}"
  return 0
}

fail() {
  local msg="$1"
  echo -e "  ${RED}✘ ${msg}${RESET}"
  exit 1
}

info() {
  local msg="$1"
  echo -e "  ${YELLOW}${msg}${RESET}"
  return 0
}

do_curl() {
  local raw
  raw=$(curl -s -w "HTTPSTATUS:%{http_code}" "$@")
  BODY="${raw%HTTPSTATUS:*}"
  STATUS="${raw##*HTTPSTATUS:}"
  return 0
}

assert_status() {
  local expected="$1"
  local label="$2"
  if [[ "$STATUS" -eq "$expected" ]]; then
    ok "$label — HTTP $STATUS"
  else
    fail "$label — expected HTTP $expected, got HTTP $STATUS"
  fi
  return 0
}

json_field() {
  local field="$1"
  python3 -c "import sys,json; print(json.load(sys.stdin)['${field}'])" <<< "$BODY" 2>/dev/null || echo "UNKNOWN"
  return 0
}

# ── 0. health-check ────────────────────────────────────────────────────────
section "0 · Health check — $BASE_URL"
do_curl "$BASE_URL/api-docs"
assert_status 200 "OpenAPI docs reachable"

# ── 1. CREATE ──────────────────────────────────────────────────────────────
UNIQUE_SUFFIX=$(date +%s)
section "1 · POST $API — create a comicbook"
do_curl -X POST "$API" \
  -H "Content-Type: application/json" \
  -d "{
    \"title\": \"Watchmen_${UNIQUE_SUFFIX}\",
    \"isbn\":  \"978-1-4012-0713-1\",
    \"date\":  \"1987-09-01\",
    \"authors\": [
      {\"name\": \"Alan Moore\",   \"role\": \"WRITER\"},
      {\"name\": \"Dave Gibbons\", \"role\": \"ARTIST\"},
      {\"name\": \"Dave Gibbons\", \"role\": \"LETTERER\"},
      {\"name\": \"John Higgins\", \"role\": \"COLORIST\"}
    ]
  }"
assert_status 201 "Create comicbook"
echo "$BODY" | pretty

COMICBOOK_ID=$(json_field id)
info "Created comicbook id: $COMICBOOK_ID"

COMICBOOK_STATUS=$(json_field status)
[[ "$COMICBOOK_STATUS" = "ACTIVE" ]] && ok "Status is ACTIVE" || fail "Expected status ACTIVE, got $COMICBOOK_STATUS"

# ── 2. LIST ────────────────────────────────────────────────────────────────
section "2 · GET $API — list all comicbooks"
do_curl "$API"
assert_status 200 "List comicbooks"
echo "$BODY" | pretty
COUNT=$(python3 -c "import sys,json; print(len(json.load(sys.stdin)))" <<< "$BODY" 2>/dev/null || echo "?")
info "Total comicbooks returned: $COUNT"

# ── 3. GET ONE ─────────────────────────────────────────────────────────────
section "3 · GET $API/$COMICBOOK_ID — get comicbook by id"
do_curl "$API/$COMICBOOK_ID"
assert_status 200 "Get comicbook by id"
echo "$BODY" | pretty

# ── 4. UPDATE ──────────────────────────────────────────────────────────────
section "4 · PUT $API/$COMICBOOK_ID — update comicbook"
do_curl -X PUT "$API/$COMICBOOK_ID" \
  -H "Content-Type: application/json" \
  -d "{
    \"title\": \"Watchmen — Definitive Edition_${UNIQUE_SUFFIX}\",
    \"isbn\":  \"978-1-4012-0713-1\",
    \"date\":  \"1987-09-01\",
    \"authors\": [
      {\"name\": \"Alan Moore\",   \"role\": \"WRITER\"},
      {\"name\": \"Dave Gibbons\", \"role\": \"ARTIST\"},
      {\"name\": \"John Higgins\", \"role\": \"COLORIST\"}
    ]
  }"
assert_status 200 "Update comicbook"
echo "$BODY" | pretty
UPDATED_TITLE=$(json_field title)
[[ "$UPDATED_TITLE" = "Watchmen — Definitive Edition_${UNIQUE_SUFFIX}" ]] \
  && ok "Title updated" \
  || fail "Expected updated title, got $UPDATED_TITLE"

# ── 5. DELETE (soft) ───────────────────────────────────────────────────────
section "5 · DELETE $API/$COMICBOOK_ID — soft delete"
do_curl -X DELETE "$API/$COMICBOOK_ID"
assert_status 204 "Soft delete comicbook"

# ── 6. VERIFY soft delete ──────────────────────────────────────────────────
section "6 · GET $API/$COMICBOOK_ID — verify status after soft delete"
do_curl "$API/$COMICBOOK_ID"
assert_status 200 "Comicbook still exists after soft delete"
echo "$BODY" | pretty
DELETED_STATUS=$(json_field status)
[[ "$DELETED_STATUS" = "DELETED" ]] && ok "Status is DELETED — row preserved" || fail "Expected status DELETED, got $DELETED_STATUS"

# ── 7. NOT FOUND ───────────────────────────────────────────────────────────
section "7 · GET $API/00000000-0000-0000-0000-000000000000 — unknown id"
do_curl "$API/00000000-0000-0000-0000-000000000000"
assert_status 404 "Unknown id returns 404"

# ── summary ────────────────────────────────────────────────────────────────
echo -e "\n${GREEN}${BOLD}All checks passed.${RESET}\n"
