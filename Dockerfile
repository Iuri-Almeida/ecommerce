FROM ubuntu

RUN apt-get update && apt-get install -y \
    docker-compose && \
    rm -rf /var/lib/apt/lists/*

RUN systemctl start docker

WORKDIR /app

COPY . .

CMD ["docker-compose", "up", "--build"]