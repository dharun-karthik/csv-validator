import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import routeHandler.Post

class ServerUnitTest {

    @Test
    fun shouldBeAbleToParseJsonArray() {
        val values = """[
              {
                "fieldName": "ProductId",
                "type": "AlphaNumeric",
                "length": 5
              },
              {
                "fieldName": "Price",
                "type": "Number"
              },
              {
                "fieldName": "Export",
                "type": "Boolean",
                "values": [
                  "Y",
                  "N"
                ]
              },
              {
                "fieldName": "Source",
                "type": "Alphabets"
              }
             ]
            """.trimMargin()
        val post = Post();
        val jsonArray = post.getMetaData(values)
        val data = jsonArray[0]

        Assertions.assertEquals("ProductId", data.fieldName)
    }
}