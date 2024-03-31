package griffio

import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import griffio.queries.Sample

import org.postgresql.ds.PGSimpleDataSource

private fun getSqlDriver() = PGSimpleDataSource().apply {
    setURL("jdbc:postgresql://localhost:5432/recipes")
    applicationName = "App Main"
}.asJdbcDriver()

fun main() {
    val driver = getSqlDriver()
    val sample = Sample(driver)

    sample.recipeQueries.getRecipe("""{"recipe_name": "Basic Fruit Salad"}""").executeAsOne().also(::println)

    val pizza = sample.recipeQueries.add(
        """
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
    """.trimIndent()
    ).executeAsOne().also(::println)

    sample.recipeQueries.update(
        """{"steps", 1}""",
        """{"step": "Serve into dishes and pour over with single cream"}""", pizza.id).executeAsOne()

    sample.recipeQueries.get(pizza.id).executeAsOne().also(::println)

    sample.recipeQueries.remove("{steps, -1}", pizza.id)

    sample.recipeQueries.get(pizza.id).executeAsOne().also(::println)

    sample.recipeQueries.contains("ingredients").executeAsList().also(::println)

}
