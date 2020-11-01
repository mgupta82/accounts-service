# Accounts Service

This service is responsible for maintaining retrieving account details and it transactions

## Local Development

### Run Unit test

``./gradlew clean test``


### Running App
Start Dependencies like postgres DB

``docker-compose up -d``

``docker-compose down``

Start Application with dev profile

``./gradlew bootRun --args='--spring.profiles.active=dev'``

Check Actuator Health endpoint

``curl http://localhost:8090/actuator/health``

### Test using swagger-ui
``http://localhost:8090/swagger-ui/index.html``

### Swagger for building UI
``http://localhost:8090/v2/api-docs``

## Create Infra

Jenkinsfile.infra can be used to run below command for creating ec2 instance

``aws cloudformation deploy --stack-name accounts-ec2 --template-file ./ec2-infra.yaml --parameter-overrides SSHKey=ec2web``

## CI

Jenkinsfile can be used in Multibranch pipeline setup on Jenkins to build project and create artifacts

Build Artifacts

``./gradlew clean build``

Build Docker Images

``docker build . -t accounts-service:test``

Login to docker hub / artifactory

``docker login``

Tag  docker images for docker hub / artifactory

``docker tag  accounts-service:test docker.io/mgupta82/accounts-service:latest``

Publish docker image with latest tag

``docker push docker.io/mgupta82/accounts-service:latest``

## CD
Jenkinsfile.deploy is used to run docker images created by CI along with its dependencies.

Login to ec2 instance

````ssh -i ~/.ssh/ec2web.pem ec2-user@accounts.mukeshgupta.info````

Start Dependencies and applications (ST Env)

``docker run -d --name postgresdb -p 5432:5432 -e POSTGRES_PASSWORD=secret postgres:12``

``docker run -d --name accountsapp -p 80:8090 -e SPRING_PROFILES_ACTIVE=st mgupta82/accounts-service``

### SIT and PROD env

We should be using docker orchestration platform like Openshift




