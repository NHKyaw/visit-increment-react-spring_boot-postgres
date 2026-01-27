#!/bin/bash
set -e
cd ~/docker/
id >> ~/docker/data.log
whoami >> ~/docker/data.log
groups >> ~/docker/data.log
printenv | grep -i IMAGE_TAG || (echo "IMAGE_TAG is not set") >> ~/docker/data.log

docker --version >> ~/docker/docker.log
sudo docker --version > ~/docker/docker.log
/user/bin/docker --version >> ~/docker/docker.log
sudo /usr/bin/docker --version >> ~/docker/docker.log

# sudo usermod -aG docker $USER || echo "Failed to add $USER to docker group"
# echo "Using docker at: $(which docker)"
# /usr/bin/docker --version
# /usr/bin/docker compose version

# /usr/bin/docker pull nhkyaw/visit-record-app:backend-${IMAGE_TAG}
# /usr/bin/docker pull nhkyaw/visit-record-app:frontend-${IMAGE_TAG}

# /usr/bin/docker compose down
# /usr/bin/docker compose --env-file .env.${IMAGE_TAG} -f docker-compose.yaml up -d