# bill

## Tools / IDEs

    brew install java gradle node git

VS Code

IntelliJ

Github Desktop

Docker Desktop

## Getting started

I used this remix tutorial to get started:

https://remix.run/docs/en/main/start/tutorial

At the "Validating Params and throwing responses" section I switched to the backend.

## Server

I used this page to create a template spring boot project:

https://start.spring.io

Click Dependencies and select Spring Web, Spring Data JPA, and MySQL Driver.

I named the artifact `server`, downloaded it, then unziped it:

    unzip ~/Downloads/server.zip

I copied the contacts data from the React project into the java project and added some code to load it into a repository.

Back in the React code, the `data.ts` file was updated to load contacts from the local server.

## Running with docker compose

    docker compose up