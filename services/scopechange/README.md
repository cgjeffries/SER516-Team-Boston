# scopechange

Scope change service

## Build

Ensure you have [buildkit](https://docs.docker.com/build/buildkit/) installed. At the root of the repository, run:

```sh
DOCKER_BUILDKIT=1 docker build -f services/scopechange/Dockerfile --build-arg PORT=9001 -t scopechange .
```

Note that `PORT` can be any unused port, it does not have to be `9001`

## Run
```sh
docker run -p 9001:9001 scopechange:latest
```

If you changed the port during the build step, be sure to change the port binding (`-p 9001:9001`) to reflect the change.
