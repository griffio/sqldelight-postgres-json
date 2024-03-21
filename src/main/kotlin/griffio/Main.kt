package griffio

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import griffio.queries.Sample

import org.postgresql.ds.PGSimpleDataSource

private fun getSqlDriver(): SqlDriver {
    val datasource = PGSimpleDataSource()
    datasource.setURL("jdbc:postgresql://localhost:5432/postgres")
    datasource.applicationName = "App Main"
    return datasource.asJdbcDriver()
}

fun main() {
    val driver = getSqlDriver()
    val sample = Sample(driver)
    // return JSONB column as String
    sample.recipeQueries.get(1).executeAsOne().also(::println)
    // insert JSONB column as String

    sample.recipeQueries.add("""
        {
          "recipe_name": "Give a slice of Pizza",
          "ingredients": [
            {
              "pizza": {
                "amounts": [
                  {
                    "amount": 1,
                    "unit": "slice"
                  }
                ]
              }
            }
          ],
          "steps": [
            {
              "step": "Cut out an equal slice from the whole pizza."
            }
          ]
        }
    """.trimIndent()).executeAsOne().also(::println)
}
