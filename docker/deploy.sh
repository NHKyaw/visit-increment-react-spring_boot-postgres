!#/bin/bash
set -e      

sudo docker pull nhkyaw/visit-record-app:backend-${IMAGE_TAG}
sudo docker pull nhkyaw/visit-record-app:frontend-${IMAGE_TAG}

sudo docker compose down
sudo docker compose --env-file .env.${IMAGE_TAG} -f docker-compose.yaml up -d