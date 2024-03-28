# PelicanApp
Pelican Travel Task

Prerequisities:
* docker 3, docker compose
* java 17
* IDE setup for lombok
* maven setup

Instructions for the first run:

* kill the processes listening on ports 9090 (springboot appliaction) and 3306 (mysql service) to avoid ports conflicts
* stop & remove the containers named pelicanapp-pelican-api-1 and pelican-mysql
* clone the project

Possibility 1: development friendly

    * Run as java application from src/main/java/com/pelicanapi/PelicanApiApplication.java with dev maven profile setup
    * Application uses Maven resource plugin with resource filtering - variables in resource files are replaced with in pom values from dev profile
    * After the initiation of the app run - the springboot docker compose will execute the docker compose from resources - creation of testing mysql db + post table creation
    * Tested with Eclipse IDE
	* tested on both windows and linux
Possibility 2 - docker compose in the root of the project:

    * create executable jar - root of the projet - command line - mvn clean install - this will create an executable jar archive in the target folder in the root of the project
    * run the docker compose up --build - root of the projet - command line - 2 services - mysql container named 'pelican-mysql' + springboot application
    * mysql container contains 20 seconds healthcheck - user creation + permissions + post table (mysql init file)
    * on healthy - springboot application jar is copied to container and run 
    * containers share the network with their host (reusage of the localhost ip defined in dev profile)
	* I was not able to access the ports of the app on Windows machine, working fine on Linux Mint

Mysql database connection (dev):

    port:3306
    username: myuser
    password: secret
    connection string: jdbc:mysql://localhost:3306/

Application url: http://localhost:9090


Swagger Endpoint documentation: http://localhost:9090/swagger-ui/index.html
