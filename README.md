# JSpecify Demo

Small Spring Boot demo for introducing JSpecify and NullAway.

## Tech stack

- Java 25
- Spring Boot 4.1.1
- Maven
- Spring MVC
- Spring JDBC
- H2
- Jakarta Validation
- JSpecify 1.0.0
- NullAway 0.14.0 through Error Prone 2.50.0

Note that Jakarta Validation is used for validating runtime request input, 
which can go hand-in-hand with JSpecify's static contracts.

## Project structure


The project has two branches:
- `main` contains the code without JSpecify annotations.
- `jspecify` contains the code with JSpecify annotations.


NullAway is configured with:

```text
-XepDisableAllChecks
-Xep:NullAway:ERROR
-XepOpt:NullAway:OnlyNullMarked=true
-XepOpt:NullAway:JSpecifyMode=true
```

## Run

```bash
mvn spring-boot:run
```

Use JDK 25 to run Maven.

## API

```text
GET  /api/nodes
GET  /api/nodes?district=Kallio
GET  /api/nodes/{id}
GET  /api/nodes/{id}/signals
POST /api/nodes
```

Sample calls are in `requests.http`.

## Deliberate nullness violations

The code deliberately contains ambiguities that plain Java accepts:

- `NodeRepository.findById()` returns `null` if there is no row, but its signature does not say so.
- `Node.alias`, `Node.operator`, and `Node.decommissionedAt` can be `null`.
- the optional `district` request parameter is unannotated.
- `Signal<String>` currently carries nullable payloads.
- `NodeController.node()` trusts a possibly missing node and can throw an NPE for an unknown id.
