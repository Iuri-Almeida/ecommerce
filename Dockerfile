FROM docker/compose

WORKDIR /app

COPY . .

ENTRYPOINT ["docker-compose", "up", "--build"]