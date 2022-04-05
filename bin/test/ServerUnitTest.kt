import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

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
        val server = Server(3001)
        val jsonArray = server.getMetaData(values)
        val data = jsonArray[0]

        Assertions.assertEquals("ProductId", data.fieldName)
    }
}