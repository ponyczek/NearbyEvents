# Events Nearby
A REST Api application that gets the events for a given city.

| WARNING:  At the moment this application only supports Berlin! |
| --- |

Data source: [berlin.de](https://www.berlin.de/sen/web/service/maerkte-feste/strassen-volksfeste/index.php/index/all.json?q=)

## Context
- This is a very basic implementation of the service.
- The application was written in such a way that it could be easily extended.
- The endpoints have been designed in away that will allow supporting different cities

## Installation

```bash
git clone https://github.com/ponyczek/NearbyEvents.git
cd NearbyEvents
mvn clean install
java -jar target/nearby-events-0.0.1-SNAPSHOT.jar
```

You can also open the project from IntelliJ by going to `File|Open|...` and selecting the `pom.xml` file in the project.

## Exposed endpoint

The application is running by default on port `8080`, therefore if you are running the application locally it will run on [http://localhost:8080](http://localhost:8080).

| Path             | Method | Parms | Example Request                  |
| ---------------- | ------ | ----- | -------------------------------- |
| /api/v1/events/{city} | GET    | city  | /api/v1/events/berlin |


## Future work
- Support different cities
- Add other API providers
- Unify the response, so it can be the same no matter who is the data provider.

## Techstack used in this project

- Java 11
- Maven
- Mockito/jUnit
- Webflux
- Springboot 2.4.3
- Lombok

## License

[MIT](https://choosealicense.com/licenses/mit/)
