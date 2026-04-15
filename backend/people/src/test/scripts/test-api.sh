#!/usr/bin/env bash
# ---------------------------------------------------------------------------
# test-api.sh — smoke test for the People REST API
# Usage: ./test-api.sh [BASE_URL]
#   BASE_URL defaults to http://localhost:8081
# Compatible with macOS (BSD) and Linux.
# ---------------------------------------------------------------------------
set -euo pipefail

BASE_URL="${1:-http://localhost:8081}"
API="$BASE_URL/api/v1/people"

# ── colours ────────────────────────────────────────────────────────────────
RED='\033[0;31m'; GREEN='\033[0;32m'; YELLOW='\033[1;33m'
CYAN='\033[0;36m'; BOLD='\033[1m'; RESET='\033[0m'

# ── helpers ────────────────────────────────────────────────────────────────
pretty() {
  if command -v jq &>/dev/null; then jq '.'; else python3 -m json.tool 2>/dev/null || cat; fi
}

section() { echo -e "\n${CYAN}${BOLD}▶ $1${RESET}"; }
ok()      { echo -e "  ${GREEN}✔ $1${RESET}"; }
fail()    { echo -e "  ${RED}✘ $1${RESET}"; exit 1; }
info()    { echo -e "  ${YELLOW}$1${RESET}"; }

# Execute a curl call and split body / status code.
# The response is written as: <body>HTTPSTATUS:<code>
# Works on macOS and Linux (no head -n -1).
do_curl() {
  local RAW
  RAW=$(curl -s -w "HTTPSTATUS:%{http_code}" "$@")
  BODY="${RAW%HTTPSTATUS:*}"
  STATUS="${RAW##*HTTPSTATUS:}"
}

assert_status() {
  local expected="$1" label="$2"
  if [ "$STATUS" -eq "$expected" ]; then
    ok "$label — HTTP $STATUS"
  else
    fail "$label — expected HTTP $expected, got HTTP $STATUS"
  fi
}

json_field() {
  python3 -c "import sys,json; print(json.load(sys.stdin)['$1'])" <<< "$BODY" 2>/dev/null || echo "UNKNOWN"
}

# ── 0. health-check ────────────────────────────────────────────────────────
section "0 · Health check — $BASE_URL"
do_curl "$BASE_URL/api-docs"
assert_status 200 "OpenAPI docs reachable"

# ── 1. CREATE ──────────────────────────────────────────────────────────────
section "1 · POST $API — create a person"
do_curl -X POST "$API" \
  -H "Content-Type: application/json" \
  -d '{
    "firstname": "Jane",
    "lastname":  "Doe",
    "nickname":  "jdoe",
    "email":     "jane.doe@example.com",
    "role":      "USER",
    "password":  "s3cr3t!"
  }'
assert_status 201 "Create person"
echo "$BODY" | pretty

PERSON_ID=$(json_field id)
info "Created person id: $PERSON_ID"

if echo "$BODY" | grep -q '"password"'; then
  fail "Password must not appear in the response"
else
  ok "Password not exposed in response"
fi

PERSON_STATUS=$(json_field status)
[ "$PERSON_STATUS" = "CREATED" ] && ok "Status is CREATED" || fail "Expected status CREATED, got $PERSON_STATUS"

# ── 2. LIST ────────────────────────────────────────────────────────────────
section "2 · GET $API — list all people"
do_curl "$API"
assert_status 200 "List people"
echo "$BODY" | pretty
COUNT=$(python3 -c "import sys,json; print(len(json.load(sys.stdin)))" <<< "$BODY" 2>/dev/null || echo "?")
info "Total people returned: $COUNT"

# ── 3. GET ONE ─────────────────────────────────────────────────────────────
section "3 · GET $API/$PERSON_ID — get person by id"
do_curl "$API/$PERSON_ID"
assert_status 200 "Get person by id"
echo "$BODY" | pretty

# ── 4. UPDATE ──────────────────────────────────────────────────────────────
section "4 · PUT $API/$PERSON_ID — update person"
do_curl -X PUT "$API/$PERSON_ID" \
  -H "Content-Type: application/json" \
  -d '{
    "firstname": "Jane",
    "lastname":  "Smith",
    "nickname":  "jsmith",
    "email":     "jane.smith@example.com",
    "role":      "ADMIN"
  }'
assert_status 200 "Update person"
echo "$BODY" | pretty
UPDATED_LASTNAME=$(json_field lastname)
[ "$UPDATED_LASTNAME" = "Smith" ] && ok "Lastname updated to Smith" || fail "Expected lastname Smith, got $UPDATED_LASTNAME"

# ── 5. DELETE (soft) ───────────────────────────────────────────────────────
section "5 · DELETE $API/$PERSON_ID — soft delete"
do_curl -X DELETE "$API/$PERSON_ID"
assert_status 204 "Soft delete person"

# ── 6. VERIFY soft delete ──────────────────────────────────────────────────
section "6 · GET $API/$PERSON_ID — verify status after soft delete"
do_curl "$API/$PERSON_ID"
assert_status 200 "Person still exists after soft delete"
echo "$BODY" | pretty
DELETED_STATUS=$(json_field status)
[ "$DELETED_STATUS" = "DELETED" ] && ok "Status is DELETED — row preserved" || fail "Expected status DELETED, got $DELETED_STATUS"

# ── 7. NOT FOUND ───────────────────────────────────────────────────────────
section "7 · GET $API/00000000-0000-0000-0000-000000000000 — unknown id"
do_curl "$API/00000000-0000-0000-0000-000000000000"
assert_status 404 "Unknown id returns 404"

# ── summary ────────────────────────────────────────────────────────────────
echo -e "\n${GREEN}${BOLD}All checks passed.${RESET}\n"
