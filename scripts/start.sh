#!/usr/bin/env bash
set -euo pipefail

PROFILE="${SPRING_PROFILES_ACTIVE:-dev}"
echo "Starting Orders API with profile: $PROFILE"

if [ "$PROFILE" = "prod" ]; then
  : "${DB_URL:?Set DB_URL for prod}"
  : "${DB_USER:?Set DB_USER for prod}"
  : "${DB_PASSWORD:?Set DB_PASSWORD for prod}"
fi

mvn -DskipTests spring-boot:run
