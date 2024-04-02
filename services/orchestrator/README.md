# orchestrator

A service for orchstrating metric microservices.

## Build

Ensure you have [buildkit](https://docs.docker.com/build/buildkit/) installed. At the root of the repository, run:

```sh
DOCKER_BUILDKIT=1 docker build -f services/orchestrator/Dockerfile --build-arg PORT=8000 -t orchestrator .
```

Note that `PORT` can be any unused port, it does not have to be `8000`

## Run
```sh
docker run -p 8000:8000 orchestrator:latest
```

If you changed the port during the build step, be sure to change the port binding (`-p 8000:8000`) to reflect the change.
