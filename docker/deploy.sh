!#/bin/bash
set -e      

docker pull nhkyaw/visit-record-app:backend-${IMAGE_TAG}
docker pull nhkyaw/visit-record-app:frontend-${IMAGE_TAG}

docker compose down
docker compose --env-file .env.${IMAGE_TAG} -f docker-compose.yaml up -d