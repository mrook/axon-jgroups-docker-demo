Axon Framework, JGroups and Docker
==================================

This is a simple project to demonstrate how to get Axon Framework and
JGroups working together in a Docker environment.

Note: this project is based on Axon Framework 3.0 (the latest available
milestone)

For more information about the JGroups integration in Axon, see
http://www.axonframework.org/docs/2.4/command-handling.html#d5e727


## Prerequisites

- Docker Compose (https://docs.docker.com/compose/install/)

## Running

    $ mvn package docker:build
    $ docker-compose up

You should see a few things:

- Two containers will be started
- They will form a JGroups cluster
- The first container will send 100 commands to Axon
- Axon will distribute the commands evenly over both containers
