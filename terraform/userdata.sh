#!/bin/bash
set -e

apt update -y
apt install -y ca-certificates curl gnupg

curl -fsSL https://get.docker.com | sh
usermod -aG docker ubuntu

mkdir -p /opt/app
chown ubuntu:ubuntu /opt/app

# Docker Compose v2
mkdir -p /usr/local/lib/docker/cli-plugins
curl -SL https://github.com/docker/compose/releases/download/v2.25.0/docker-compose-linux-x86_64 \
  -o /usr/local/lib/docker/cli-plugins/docker-compose
chmod +x /usr/local/lib/docker/cli-plugins/docker-compose
