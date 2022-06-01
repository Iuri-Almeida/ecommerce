FROM openjdk:18-alpine

WORKDIR /app

COPY . .

ENTRYPOINT ["docker", "compose", "up", "--build"]