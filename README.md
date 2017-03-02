Axon Framework, JGroups and Docker
==================================

This is a simple project to demonstrate how to get Axon Framework (3.x) and
JGroups working together in a Docker environment.

For more information about the JGroups integration in Axon, see
https://docs.axonframework.org/v/3.0/part3/command-dispatching.html


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
