# burndown

A service for the taskexcess microservice.

## Build

Ensure you have [buildkit](https://docs.docker.com/build/buildkit/) installed. At the root of the repository, run:

```sh
DOCKER_BUILDKIT=1 docker build -f services/burndown/Dockerfile --build-arg PORT=9007 -t burndown .
```

Note that `PORT` can be any unused port, it does not have to be `9007`

## Run
```sh
docker run -p 9007:9007 burndown:latest
```

If you changed the port during the build step, be sure to change the port binding (`-p 9007:9007`) to reflect the change.