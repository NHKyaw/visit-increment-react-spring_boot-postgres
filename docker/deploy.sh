#!/bin/bash
set -euo pipefail

# Ensure PATH for non-interactive shells
export PATH="/usr/bin:/usr/local/bin:/snap/bin:$PATH"

echo "Using docker at: $(which docker)"
docker --version
docker compose version

docker pull nhkyaw/visit-record-app:backend-${IMAGE_TAG}
docker pull nhkyaw/visit-record-app:frontend-${IMAGE_TAG}

docker compose down
docker compose --env-file .env.${IMAGE_TAG} -f docker-compose.yaml up -d
