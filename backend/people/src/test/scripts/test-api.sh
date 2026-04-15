#!/usr/bin/env bash
# ---------------------------------------------------------------------------
# test-api.sh — smoke test for the People REST API
# Usage: ./test-api.sh [BASE_URL]
#   BASE_URL defaults to http://localhost:8081
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

ok()   { echo -e "  ${GREEN}✔ $1${RESET}"; }
fail() { echo -e "  ${RED}✘ $1${RESET}"; exit 1; }
info() { echo -e "  ${YELLOW}$1${RESET}"; }

assert_status() {
  local expected="$1" actual="$2" label="$3"
  if [ "$actual" -eq "$expected" ]; then
    ok "$label — HTTP $actual"
  else
    fail "$label — expected HTTP $expected, got HTTP $actual"
  fi
}

# ── 0. health-check ────────────────────────────────────────────────────────
section "0 · Health check — $BASE_URL"
STATUS=$(curl -s -o /dev/null -w "%{http_code}" "$BASE_URL/api-docs")
assert_status 200 "$STATUS" "OpenAPI docs reachable"

# ── 1. CREATE ──────────────────────────────────────────────────────────────
section "1 · POST $API — create a person"
RESPONSE=$(curl -s -w "\n%{http_code}" -X POST "$API" \
  -H "Content-Type: application/json" \
  -d '{
    "firstname": "Jane",
    "lastname":  "Doe",
    "nickname":  "jdoe",
    "email":     "jane.doe@example.com",
    "role":      "USER",
    "password":  "s3cr3t!"
  }')
BODY=$(echo "$RESPONSE" | head -n -1)
STATUS=$(echo "$RESPONSE" | tail -n 1)
assert_status 201 "$STATUS" "Create person"
echo "$BODY" | pretty
PERSON_ID=$(echo "$BODY" | python3 -c "import sys,json; print(json.load(sys.stdin)['id'])" 2>/dev/null \
  || echo "$BODY" | grep -o '"id":"[^"]*"' | head -1 | cut -d'"' -f4)
info "Created person id: $PERSON_ID"

# Check password is not returned
if echo "$BODY" | grep -q '"password"'; then
  fail "Password must not appear in the response"
else
  ok "Password not exposed in response"
fi

# Check status is CREATED
PERSON_STATUS=$(echo "$BODY" | python3 -c "import sys,json; print(json.load(sys.stdin)['status'])" 2>/dev/null || echo "UNKNOWN")
[ "$PERSON_STATUS" = "CREATED" ] && ok "Status is CREATED" || fail "Expected status CREATED, got $PERSON_STATUS"

# ── 2. LIST ────────────────────────────────────────────────────────────────
section "2 · GET $API — list all people"
RESPONSE=$(curl -s -w "\n%{http_code}" "$API")
BODY=$(echo "$RESPONSE" | head -n -1)
STATUS=$(echo "$RESPONSE" | tail -n 1)
assert_status 200 "$STATUS" "List people"
echo "$BODY" | pretty
COUNT=$(echo "$BODY" | python3 -c "import sys,json; print(len(json.load(sys.stdin)))" 2>/dev/null || echo "?")
info "Total people returned: $COUNT"

# ── 3. GET ONE ─────────────────────────────────────────────────────────────
section "3 · GET $API/$PERSON_ID — get person by id"
RESPONSE=$(curl -s -w "\n%{http_code}" "$API/$PERSON_ID")
BODY=$(echo "$RESPONSE" | head -n -1)
STATUS=$(echo "$RESPONSE" | tail -n 1)
assert_status 200 "$STATUS" "Get person by id"
echo "$BODY" | pretty

# ── 4. UPDATE ──────────────────────────────────────────────────────────────
section "4 · PUT $API/$PERSON_ID — update person"
RESPONSE=$(curl -s -w "\n%{http_code}" -X PUT "$API/$PERSON_ID" \
  -H "Content-Type: application/json" \
  -d '{
    "firstname": "Jane",
    "lastname":  "Smith",
    "nickname":  "jsmith",
    "email":     "jane.smith@example.com",
    "role":      "ADMIN"
  }')
BODY=$(echo "$RESPONSE" | head -n -1)
STATUS=$(echo "$RESPONSE" | tail -n 1)
assert_status 200 "$STATUS" "Update person"
echo "$BODY" | pretty
UPDATED_LASTNAME=$(echo "$BODY" | python3 -c "import sys,json; print(json.load(sys.stdin)['lastname'])" 2>/dev/null || echo "?")
[ "$UPDATED_LASTNAME" = "Smith" ] && ok "Lastname updated to Smith" || fail "Expected lastname Smith, got $UPDATED_LASTNAME"

# ── 5. DELETE (soft) ───────────────────────────────────────────────────────
section "5 · DELETE $API/$PERSON_ID — soft delete"
STATUS=$(curl -s -o /dev/null -w "%{http_code}" -X DELETE "$API/$PERSON_ID")
assert_status 204 "$STATUS" "Soft delete person"

# ── 6. VERIFY soft delete ──────────────────────────────────────────────────
section "6 · GET $API/$PERSON_ID — verify status after soft delete"
RESPONSE=$(curl -s -w "\n%{http_code}" "$API/$PERSON_ID")
BODY=$(echo "$RESPONSE" | head -n -1)
STATUS=$(echo "$RESPONSE" | tail -n 1)
assert_status 200 "$STATUS" "Person still exists after soft delete"
echo "$BODY" | pretty
DELETED_STATUS=$(echo "$BODY" | python3 -c "import sys,json; print(json.load(sys.stdin)['status'])" 2>/dev/null || echo "UNKNOWN")
[ "$DELETED_STATUS" = "DELETED" ] && ok "Status is DELETED — row preserved" || fail "Expected status DELETED, got $DELETED_STATUS"

# ── 7. NOT FOUND ───────────────────────────────────────────────────────────
section "7 · GET $API/00000000-0000-0000-0000-000000000000 — unknown id"
STATUS=$(curl -s -o /dev/null -w "%{http_code}" "$API/00000000-0000-0000-0000-000000000000")
assert_status 404 "$STATUS" "Unknown id returns 404"

# ── summary ────────────────────────────────────────────────────────────────
echo -e "\n${GREEN}${BOLD}All checks passed.${RESET}\n"
