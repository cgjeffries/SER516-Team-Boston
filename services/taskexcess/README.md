# taskexcess

A service for the taskexcess microservice.

## Build

Ensure you have [buildkit](https://docs.docker.com/build/buildkit/) installed. At the root of the repository, run:

```sh
DOCKER_BUILDKIT=1 docker build -f services/taskexcess/Dockerfile --build-arg PORT=9003 -t taskexcess .
```

Note that `PORT` can be any unused port, it does not have to be `9003`

## Run
```sh
docker run -p 9003:9003 taskexcess:latest
```

If you changed the port during the build step, be sure to change the port binding (`-p 9003:9003`) to reflect the change.