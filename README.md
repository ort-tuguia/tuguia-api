# TuGuia API

![technology Kotlin](https://img.shields.io/badge/technology-kotlin-purple.svg)
![technology SpringBoot](https://img.shields.io/badge/technology-spring-green.svg)

TuGuia API

## Contents

-   [Getting Started](#getting-started)
-   [Documentation](#documentation)
-   [Developed By](#developed-by)

## Getting Started

```bash
git clone git@github.com:ort-tuguia/tuguia-api.git && cd tuguia-api
```

### Services

#### Database

This project use a PostgreSQL Database.

To initialize local PostgreSQL Database with Docker execute:

```bash
docker-compose up -d
```

So to initialize the project locally, the following environment variables need to be set:

-   **DATABASE_HOST** (default: localhost)
-   **DATABASE_PORT** (default: 5432)
-   **DATABASE_NAME** (default: tuguia)
-   **DATABASE_USERNAME** (default: postgres)
-   **DATABASE_PASSWORD** (default: password)

Default environment variables to copy and paste in IDE:

```
DATABASE_HOST=localhost;DATABASE_NAME=tuguia;DATABASE_PASSWORD=password;DATABASE_PORT=5432;DATABASE_USERNAME=postgres
```

## Documentation

### Open API Definition / Swagger

-   [Production (On Heroku)](https://ort-tuguia-api.herokuapp.com/swagger-ui/index.html)
-   [Local](http://localhost:8080/swagger-ui/index.html)

### Manuals

-   [Developer Manual](https://docs.google.com/document/d/11avoCSS9cVkUSJQ-y1uf5iqZlVy-1enZ)

## Developed By

-   [@AgusVenturelli](https://github.com/AgusVenturelli)
-   [@alejandrogrosso](https://github.com/alejandrogrosso)
-   [@daichanmiyashiro](https://github.com/daichanmiyashiro)
