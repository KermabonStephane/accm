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

json_array_field() {
  local index="$1" field="$2"
  python3 -c "import sys,json; print(json.load(sys.stdin)[${index}]['${field}'])" <<< "$BODY" 2>/dev/null || echo "UNKNOWN"
  return 0
}

json_array_len() {
  python3 -c "import sys,json; print(len(json.load(sys.stdin)))" <<< "$BODY" 2>/dev/null || echo "?"
  return 0
}

# ── 0. health-check ────────────────────────────────────────────────────────
section "0 · Health check — $BASE_URL"
do_curl "$BASE_URL/api-docs"
assert_status 200 "OpenAPI docs reachable"

# ── 1. CREATE ──────────────────────────────────────────────────────────────
UNIQUE_SUFFIX=$(date +%s)
section "1 · POST $API — create a comicbook with authors"
do_curl -X POST "$API" \
  -H "Content-Type: application/json" \
  -d "{
    \"title\": \"Watchmen_${UNIQUE_SUFFIX}\",
    \"isbn\":  \"978-1-4012-0713-1\",
    \"date\":  \"1987-09-01\",
    \"authors\": [
      {\"firstname\": \"Alan\",  \"lastname\": \"Moore\",   \"role\": \"WRITER\"},
      {\"firstname\": \"Dave\",  \"lastname\": \"Gibbons\", \"role\": \"ARTIST\"},
      {\"firstname\": \"Dave\",  \"lastname\": \"Gibbons\", \"role\": \"LETTERER\"},
      {\"firstname\": \"John\",  \"lastname\": \"Higgins\", \"role\": \"COLORIST\"}
    ]
  }"
assert_status 201 "Create comicbook"
echo "$BODY" | pretty

COMICBOOK_ID=$(json_field id)
info "Created comicbook id: $COMICBOOK_ID"

COMICBOOK_STATUS=$(json_field status)
[[ "$COMICBOOK_STATUS" = "ACTIVE" ]] && ok "Status is ACTIVE" || fail "Expected status ACTIVE, got $COMICBOOK_STATUS"

ALAN_MOORE_ID=$(python3 -c "import sys,json; authors=json.load(sys.stdin)['authors']; print(next(a['id'] for a in authors if a['lastname']=='Moore'))" <<< "$BODY")
info "Alan Moore author id: $ALAN_MOORE_ID"

# ── 2. LIST ────────────────────────────────────────────────────────────────
section "2 · GET $API — list all comicbooks"
do_curl "$API"
assert_status 200 "List comicbooks"
echo "$BODY" | pretty
COUNT=$(json_array_len)
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
      {\"firstname\": \"Alan\", \"lastname\": \"Moore\",   \"role\": \"WRITER\"},
      {\"firstname\": \"Dave\", \"lastname\": \"Gibbons\", \"role\": \"ARTIST\"},
      {\"firstname\": \"John\", \"lastname\": \"Higgins\", \"role\": \"COLORIST\"}
    ]
  }"
assert_status 200 "Update comicbook"
echo "$BODY" | pretty
UPDATED_TITLE=$(json_field title)
[[ "$UPDATED_TITLE" = "Watchmen — Definitive Edition_${UNIQUE_SUFFIX}" ]] \
  && ok "Title updated" \
  || fail "Expected updated title, got $UPDATED_TITLE"

# ── 5. CREATE second comicbook (for author management tests) ───────────────
section "5 · POST $API — create a second comicbook (no authors)"
do_curl -X POST "$API" \
  -H "Content-Type: application/json" \
  -d "{\"title\": \"V for Vendetta_${UNIQUE_SUFFIX}\", \"isbn\": \"978-0-930289-52-4\", \"date\": \"1988-03-01\", \"authors\": []}"
assert_status 201 "Create second comicbook"
COMICBOOK2_ID=$(json_field id)
info "Created second comicbook id: $COMICBOOK2_ID"

# ── 6. LIST AUTHORS (empty) ────────────────────────────────────────────────
section "6 · GET $API/$COMICBOOK2_ID/authors — list authors (empty)"
do_curl "$API/$COMICBOOK2_ID/authors"
assert_status 200 "List authors — empty"
echo "$BODY" | pretty
[[ "$(json_array_len)" = "0" ]] && ok "No authors yet" || fail "Expected empty authors list"

# ── 7. ADD AUTHOR ──────────────────────────────────────────────────────────
section "7 · POST $API/$COMICBOOK2_ID/authors — add Alan Moore as WRITER"
do_curl -X POST "$API/$COMICBOOK2_ID/authors" \
  -H "Content-Type: application/json" \
  -d "{\"authorId\": \"$ALAN_MOORE_ID\", \"role\": \"WRITER\"}"
assert_status 201 "Add author"
echo "$BODY" | pretty
ADDED_LASTNAME=$(json_field lastname)
[[ "$ADDED_LASTNAME" = "Moore" ]] && ok "Correct author returned" || fail "Expected Moore, got $ADDED_LASTNAME"

# ── 8. LIST AUTHORS (one) ──────────────────────────────────────────────────
section "8 · GET $API/$COMICBOOK2_ID/authors — list authors (one)"
do_curl "$API/$COMICBOOK2_ID/authors"
assert_status 200 "List authors — one entry"
echo "$BODY" | pretty
[[ "$(json_array_len)" = "1" ]] && ok "One author listed" || fail "Expected 1 author"

# ── 9. ADD SAME AUTHOR+ROLE AGAIN (conflict) ──────────────────────────────
section "9 · POST $API/$COMICBOOK2_ID/authors — duplicate author+role → 409"
do_curl -X POST "$API/$COMICBOOK2_ID/authors" \
  -H "Content-Type: application/json" \
  -d "{\"authorId\": \"$ALAN_MOORE_ID\", \"role\": \"WRITER\"}"
assert_status 409 "Duplicate author+role returns 409"

# ── 10. ADD SAME AUTHOR WITH DIFFERENT ROLE ───────────────────────────────
section "10 · POST $API/$COMICBOOK2_ID/authors — same author, different role"
do_curl -X POST "$API/$COMICBOOK2_ID/authors" \
  -H "Content-Type: application/json" \
  -d "{\"authorId\": \"$ALAN_MOORE_ID\", \"role\": \"ARTIST\"}"
assert_status 201 "Same author with different role — HTTP 201"

# ── 11. LIST AUTHORS (two) ─────────────────────────────────────────────────
section "11 · GET $API/$COMICBOOK2_ID/authors — list authors (two)"
do_curl "$API/$COMICBOOK2_ID/authors"
assert_status 200 "List authors — two entries"
echo "$BODY" | pretty
[[ "$(json_array_len)" = "2" ]] && ok "Two authors listed" || fail "Expected 2 authors"

# ── 12. REMOVE AUTHOR ─────────────────────────────────────────────────────
section "12 · DELETE $API/$COMICBOOK2_ID/authors/$ALAN_MOORE_ID/roles/WRITER — remove WRITER role"
do_curl -X DELETE "$API/$COMICBOOK2_ID/authors/$ALAN_MOORE_ID/roles/WRITER"
assert_status 204 "Remove author role"

# ── 13. LIST AUTHORS (one remaining) ──────────────────────────────────────
section "13 · GET $API/$COMICBOOK2_ID/authors — list authors (one remaining)"
do_curl "$API/$COMICBOOK2_ID/authors"
assert_status 200 "List authors after removal"
echo "$BODY" | pretty
[[ "$(json_array_len)" = "1" ]] && ok "One author remaining" || fail "Expected 1 author after removal"
REMAINING_ROLE=$(json_array_field 0 role)
[[ "$REMAINING_ROLE" = "ARTIST" ]] && ok "ARTIST role remains" || fail "Expected ARTIST role, got $REMAINING_ROLE"

# ── 14. REMOVE AUTHOR — NOT FOUND ─────────────────────────────────────────
section "14 · DELETE $API/$COMICBOOK2_ID/authors/$ALAN_MOORE_ID/roles/WRITER — already removed → 404"
do_curl -X DELETE "$API/$COMICBOOK2_ID/authors/$ALAN_MOORE_ID/roles/WRITER"
assert_status 404 "Already-removed author-role returns 404"

# ── 15. DELETE (soft) ─────────────────────────────────────────────────────
section "15 · DELETE $API/$COMICBOOK_ID — soft delete"
do_curl -X DELETE "$API/$COMICBOOK_ID"
assert_status 204 "Soft delete comicbook"

# ── 16. VERIFY soft delete ────────────────────────────────────────────────
section "16 · GET $API/$COMICBOOK_ID — verify status after soft delete"
do_curl "$API/$COMICBOOK_ID"
assert_status 200 "Comicbook still exists after soft delete"
echo "$BODY" | pretty
DELETED_STATUS=$(json_field status)
[[ "$DELETED_STATUS" = "DELETED" ]] && ok "Status is DELETED — row preserved" || fail "Expected status DELETED, got $DELETED_STATUS"

# ── 17. NOT FOUND ─────────────────────────────────────────────────────────
section "17 · GET $API/00000000-0000-0000-0000-000000000000 — unknown id"
do_curl "$API/00000000-0000-0000-0000-000000000000"
assert_status 404 "Unknown id returns 404"

# ── summary ───────────────────────────────────────────────────────────────
echo -e "\n${GREEN}${BOLD}All checks passed.${RESET}\n"
