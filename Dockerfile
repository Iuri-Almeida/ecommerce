FROM nginx

WORKDIR /app

COPY . .

ENTRYPOINT ["docker", "compose", "up", "--build"]