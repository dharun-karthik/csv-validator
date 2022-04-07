package metaData

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MetaDataWriterTest {
    private val metaDataWriter = MetaDataWriter("src/test/kotlin/metaData/csv-meta-data-test.json")

    @Test
    fun shouldBeAbleToReadRawContent() {
        val expected = """[
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
        val actual = metaDataWriter.readRawContent()

        assertEquals(expected, actual)

    }

    @Test
    fun shouldBeAbleToGiveMetaDataInJson(){
        val fields = metaDataWriter.readFields()

        val expected = "500020"
        val actual = fields?.get(7)?.values?.get(0)

        assertEquals(expected,actual)
    }
}