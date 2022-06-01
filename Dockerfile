FROM ubuntu

RUN apt-get update && apt-get install -y \
    docker-compose && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY . .

CMD ["docker-compose", "up", "--build"]