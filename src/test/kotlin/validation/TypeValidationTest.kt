package validation

import Server
import org.json.JSONArray
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TypeValidationTest {

    @Test
    fun shouldGiveTypeErrorLinesAsResult(){
        val server = Server(3004)
        val data = """[
  {
    "fieldName": "Product Id",
    "type": "AlphaNumeric",
    "length": 5
  },
  {
    "fieldName": "Product Description",
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
    "type": "Alphabet"
  },
  {
    "fieldName": "Country Name",
    "type": "Alphabet",
    "minLength": 3
  },
  {
    "fieldName": "Source City",
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
        val jsonData = server.getMetaData(data)
        server.fieldArray = jsonData
        val csvData ="""[
    {
        "Product Id": "1564",
        "Product Description": "Table",
        "Price": "4500.59d",
        "Export": "N",
        "Source City": "Nagpur",
        "Source Pincode": "440001"
    },
    {
        "Product Id": "1234",
        "Product Description": "Chairs",
        "Price": "1000",
        "Export": "Y",
        "Country Name": "AUS",
        "Source City": "Mumbai",
        "Country Code": "61",
        "Source Pincode": "400001"
    }
]"""
        val jsonCsvData = JSONArray(csvData)
        val result = server.typeValidation(jsonCsvData)
        println(result)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsNumeric() {
        val typeValidation = TypeValidation()
        val value = "410401"

        val actual = typeValidation.isNumeric(value)

        assertTrue(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsNotNumeric() {
        val typeValidation = TypeValidation()
        val value = "410401a"

        val actual = typeValidation.isNumeric(value)

        assertFalse(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsAlphabetic() {
        val typeValidation = TypeValidation()
        val value = "abcrere"

        val actual = typeValidation.isAlphabetic(value)

        assertTrue(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsNotAlphabetic() {
        val typeValidation = TypeValidation()
        val value = "abcrere12"

        val actual = typeValidation.isAlphabetic(value)

        assertFalse(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsAlphaNumeric() {
        val typeValidation = TypeValidation()
        val value = "asfd123"

        val actual = typeValidation.isAlphaNumeric(value)

        assertTrue(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsNotAlphaNumeric() {
        val typeValidation = TypeValidation()
        val value = "asfs:ae1"

        val actual = typeValidation.isAlphaNumeric(value)

        assertFalse(actual)
    }

}