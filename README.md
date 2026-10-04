# CNPM-ModuleStatisticElo

A desktop module that reports how chess players' Elo ratings changed in a world chess management system. It is a PTIT Software Engineering (CNPM) coursework project.

## Features

- Login screen for managers.
- Elo statistics per tournament: a table of players with ID, name, birth year, nationality, initial Elo, final Elo and Elo change.
- Filtering by nationality and by Elo change.
- Drill-down from a player to their matches, and from a match to its details.
- CSV export of the player statistics (`CsvExporter`).

## Tech stack

- Java with Swing for the UI
- DAO pattern over JDBC, MySQL 8 (`mysql-connector-java` 8.0.33)
- Gradle, JUnit 5 configured for tests
- Docker Compose for the database (schema in `db/init.sql`)

## Getting started

1. Start the database (exposed on host port 3307):

   ```bash
   docker compose up -d
   ```

2. Build with `./gradlew build`, then run `org.example.Main` (for example from IntelliJ IDEA, since the project ships `.idea` settings).

The JDBC connection settings are hard-coded in `src/main/java/dao/DAO.java`; adjust them to match your database credentials.
