# taskinertia

A service for the taskinertia microservice, graphically displays task inertia over time.

## Build

Ensure you have [buildkit](https://docs.docker.com/build/buildkit/) installed. At the root of the repository, run:

```sh
DOCKER_BUILDKIT=1 docker build -f services/taskinertia/Dockerfile --build-arg PORT=9002 -t taskinertia .
```

Note that `PORT` can be any unused port, it does not have to be `9002`

## Run
```sh
docker run -p 9002:9002 taskinertia:latest
```

If you changed the port during the build step, be sure to change the port binding (`-p 9002:9002`) to reflect the change.