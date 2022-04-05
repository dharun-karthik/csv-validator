package routeHandler

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import routeHandler.PostRouteHandler

class PostTest {

    @Test
    fun shouldBeAbleToAddCsvMetaData(){
        val post = PostRouteHandler()
        val data = """[
  {
    "fieldName": "ProductId",
    "type": "AlphaNumeric",
    "length": 5
  },
  {
    "fieldName": "ProductDescription",
    "type": "AlphaNumeric",
    "minLength": 7,
    "maxLength": 20
  },
  {
    "fieldName": "Price",
    "type": "Number"
  },
  {
    "fieldName": "Export",
    "type": "Alphabet",
    "values": [
      "Y",
      "N"
    ]
  },
  {
    "fieldName": "Country Name",
    "type": "Alphabet",
    "minLength": 3
  },
  {
    "fieldName": "Source",
    "type": "Alphabet",
    "minLength": 3
  },
  {
    "fieldName": "Country Code",
    "type": "Number",
    "maxLength": 3
  },
  {
    "fieldName": "Source Pincode",
    "type": "Number",
    "length": 6,
    "values": [
      "500020",
      "110001",
      "560001",
      "500001",
      "111045",
      "230532",
      "530068",
      "226020",
      "533001",
      "600001",
      "700001",
      "212011",
      "641001",
      "682001",
      "444601"
    ]
  }
]"""
        post.addCsvMetaData(data)
        val field = post.fieldArray[0]
        assertNull(field.maxLength)
        assertEquals(5,field.length)
        assertEquals("Number",post.fieldArray[2].type)
    }
}