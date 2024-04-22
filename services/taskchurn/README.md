# taskchurn

A service for the taskchurn microservice.

## Build

Ensure you have [buildkit](https://docs.docker.com/build/buildkit/) installed. At the root of the repository, run:

```sh
DOCKER_BUILDKIT=1 docker build -f services/taskchurn/Dockerfile --build-arg PORT=9006 -t taskchurn .
```

Note that `PORT` can be any unused port, it does not have to be `9006`

## Run
```sh
docker run -p 9006:9006 taskchurn:latest
```

If you changed the port during the build step, be sure to change the port binding (`-p 9006:9006`) to reflect the change.