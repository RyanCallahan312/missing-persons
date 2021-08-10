# Missing Persons Code Sample
___
## App info

Running Spring 2.5.3 and Java 16

Builds with gradle and runs in a docker container

Api docs are created via Open API and the swagger UI which can be found when running locally at: `http://localhost:{Port defined in docker-compose.yaml}/swagger-ui.html`

Migrations managed by liquibase and db scripts are written for postgresql
___


# ***When cloning the repo make sure your machine does not change the line endings. It will break the gradle wrapper if the line endings are CR+LF***

## How to run

To run it first build the docker image for the spring app with `docker build -t missing-persons:0.1.0 -f ./src/main/resources/docker/Dockerfile .` from the root of the project

To avoid secrets being leaked template files with the .example extenion have been created in place of the files with secrets. Simply removing the .example at the end of the files should give you a working app

the two files are found at `src/main/resources/docker/docker-compose.yml.example` and `src/main/resources/application.yml.example`

After the example files are modified `docker-compose up` in `src/main/resources/docker/` to start the app

Alternatively you could modify the example files then run `docker build -t missing-persons:0.1.0 -f ./src/main/resources/docker/Dockerfile . && cd src/main/resources/docker &&  docker-compose up -d` from the root directory of the project