# taskdefectdensity

A service for the taskdefectdensity microservice.

## Build

Ensure you have [buildkit](https://docs.docker.com/build/buildkit/) installed. At the root of the repository, run:

```sh
DOCKER_BUILDKIT=1 docker build -f services/taskdefectdensity/Dockerfile --build-arg PORT=9004 -t taskdefectdensity .
```

Note that `PORT` can be any unused port, it does not have to be `9004`

## Run
```sh
docker run -p 9004:9004 taskdefectdensity:latest
```

If you changed the port during the build step, be sure to change the port binding (`-p 9004:9004`) to reflect the change.