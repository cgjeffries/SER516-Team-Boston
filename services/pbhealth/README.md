# pbhealth

A service for the pbhealth microservice, provides a configurable health rating about a project.

## Build

Ensure you have [buildkit](https://docs.docker.com/build/buildkit/) installed. At the root of the repository, run:

```sh
DOCKER_BUILDKIT=1 docker build -f services/pbhealth/Dockerfile --build-arg PORT=9000 -t pbhealth .
```

Note that `PORT` can be any unused port, it does not have to be `9000`

## Run
```sh
docker run -p 9000:9000 pbhealth:latest
```

If you changed the port during the build step, be sure to change the port binding (`-p 9000:9000`) to reflect the change.