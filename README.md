# SqlDelight 2.1.x Postgresql Json support 

https://github.com/cashapp/sqldelight

Snapshot version: 2.1.0-SNAPSHOT

Support JSON and JSONB column types and operations

String is the input and output type for the table API for JSON/JSONB columns

```sql
CREATE TABLE Recipes (
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  recipe JSONB NOT NULL,
  createdAt TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updatedAt TIMESTAMPTZ
);
```

*Not supported*
* Json Operators bind
  * e.g `SELECT * FROM Recipes WHERE recipe @> ?`
  * MERGED https://github.com/cashapp/sqldelight/pull/5100
* Json column type support
  * MERGED https://github.com/cashapp/sqldelight/issues/5028
* Json Operators
  * MERGED https://github.com/cashapp/sqldelight/issues/5040
* Json GIN indexes
  * MERGED https://github.com/cashapp/sqldelight/issues/5026

----

```shell
createdb recipes
./gradlew build
./gradlew flywayMigrate
```

Flyway db migrations
https://documentation.red-gate.com/fd/gradle-task-184127407.html
