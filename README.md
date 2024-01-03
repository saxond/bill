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

I named the artifact `server`, downloaded it, then unzipped it:

    unzip ~/Downloads/server.zip

I copied the contacts data from the React project into the java project and added some code to load it into a repository.

Back in the React code, the `data.ts` file was updated to load contacts from the local server.

## Running with docker compose

    docker compose up

## Running locally

### Running Remix app

From the app directory:

    npm run dev

### Running java server

From the root directory:

    ./gradlew run

## Testing

The java tests are split between unit and integration tests.  The unit tests use an H2 embedded Database in MySQL mode, while the integration tests use the actual MySQL database that runs in a docker container (`docker compose up db`).

A github action is set up to run `./gradlew build` with each commit.  That assembles the java distribution and runs the tests, but it does not run the integration tests.

## TODO

https://github.com/remix-run/remix/issues/806

https://blog.logrocket.com/guide-adding-google-login-react-app/