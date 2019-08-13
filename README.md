# scrape-insert-postgresql

Simple projects to taste web scraping with Scala and use different Postgresql DB connection tools.

## Some instructions to run tutorials

* To run PostgreSQL in docker-compose (first this is required to use a local PostgreSQL):

```
docker-compose -f ./simple-tutorial/docker-compose-setup/docker-compose.yml up
```

* To run the simple tutorial with **doobie**:

```
sbt "project scrape-and-doobie" run
```

* To run one level complicated tutorial with **Scala Play API**:

```
sbt "project scrape-and-play" run  
```