# APSI

Project for APSI lessons on WUT

### Running with docker and docker-compose
1. Create volume named postgresql ` docker volume create --name postgresql -d local`
2. Run `docker-compose up -d`

### Running for development

#### Easiest way:

Import as project to IntelliJ IDEA (Ultimate version supports frontend)

Run generated configurations for backend and frontend

Part of requirements from harder way may be needed.

#### Harder way:

Requirements:
* Java 11
* Maven 3.6.1 or higher
* nodeJs v12.13.x or higher
* npm 6.12.x or higher

Running Frontend

1. Jump to `/frontend` folder
2. Run `npm start`

Running Backend
1. Jum to `/backend` folder 
2. Run `mvn clean install`
3. Run `mvn spring-boot:run`