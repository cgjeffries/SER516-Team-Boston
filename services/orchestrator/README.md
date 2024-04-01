# orchestrator

A service for orchstrating metric microservices.

## Build

Ensure you have [buildkit](https://docs.docker.com/build/buildkit/) installed. At the root of the repository, run:

```sh
DOCKER_BUILDKIT=1 docker build -f services/orchestrator/Dockerfile --build-arg PORT=8000 -t orchestrator .
```


## Run
```sh
docker run -p 8000:8000 orchestrator:latest
```
