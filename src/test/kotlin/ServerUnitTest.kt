import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SeverUnitTest {

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
"""
        val server = Server()
        val jsonArray = server.getJsonBody(values)
        val data = jsonArray[0]

        assertEquals("ProductId", data.fieldName)
    }
}
