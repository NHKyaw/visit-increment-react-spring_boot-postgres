#!/bin/bash
set -Eeuo pipefail
set -x   # 🔥 SHOW EVERY COMMAND

echo "===== CONTEXT ====="
date
hostname
id
whoami
groups

echo "===== ENV ====="
printenv | grep -i IMAGE_TAG || echo "IMAGE_TAG is not set"

: "${IMAGE_TAG:?IMAGE_TAG is required}"

echo "===== DOCKER CHECK ====="
which docker
/usr/bin/docker --version
/usr/bin/docker compose version

echo "===== PULL BACKEND IMAGE ====="
/usr/bin/docker pull nhkyaw/visit-record-app:backend-${IMAGE_TAG} | tee /tmp/backend_pull.log
echo "✔ Backend image pulled"

echo "===== PULL FRONTEND IMAGE ====="
/usr/bin/docker pull nhkyaw/visit-record-app:frontend-${IMAGE_TAG} | tee /tmp/frontend_pull.log
echo "✔ Frontend image pulled"

echo "===== COMPOSE DOWN ====="
/usr/bin/docker compose down
echo "✔ Containers stopped"

echo "===== COMPOSE UP ====="
/usr/bin/docker compose \
  --env-file .env.${IMAGE_TAG} \
  -f docker-compose.yaml \
  up -d | tee /tmp/compose_up.log

echo "✔ Deployment completed"

echo "===== RUNNING CONTAINERS ====="
/usr/bin/docker ps
