package metaData

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class MetaDataReaderWriterTest {
    @Test
    fun shouldBeAbleToReadRawContent() {
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaData/csv-meta-data-test.json")
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
        val actual = metaDataReaderWriter.readRawContent()

        assertEquals(expected, actual)
    }

    @Test
    fun shouldBeAbleToGiveMetaDataInJson() {
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaData/csv-meta-data-test.json")
        val fields = metaDataReaderWriter.readFields()

        val expected = "500020"
        val actual = fields[7].values?.get(0)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldBeAbleToGiveMetaDataInJsonWhenThereIsNoContent() {
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaData/empty-json-test.json")
        val expected = metaDataReaderWriter.readFields()

        assertNotNull(expected)
    }

    @Test
    fun shouldBeAbleToWriteJsonToFile() {
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaData/write-test.json")
        val jsonData: Array<JsonMetaDataTemplate> =
            arrayOf(JsonMetaDataTemplate("test field", "Alphabet", 2, 3, 1, listOf("22")))
        metaDataReaderWriter.writeJsonContent(jsonData)

        val expected =
            """[{"fieldName":"test field","type":"Alphabet","length":2,"maxLength":3,"minLength":1,"values":["22"]}]"""
        val actual = metaDataReaderWriter.readRawContent()

        assertEquals(expected, actual)
    }

    @Test
    fun shouldBeAbleToAppendFieldToFile() {
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaData/write-test.json")
        val field = """{"fieldName": "ProductDescription","type": "AlphaNumeric","minLength": 7,"maxLength": 20}"""
        val expected =
            """[{"fieldName":"test field","type":"Alphabet","length":2,"maxLength":3,"minLength":1,"values":["22"]},{"fieldName":"ProductDescription","type":"AlphaNumeric","maxLength":20,"minLength":7}]"""

        metaDataReaderWriter.appendField(field)
        val actual = metaDataReaderWriter.readRawContent()
        metaDataReaderWriter.clearFields()

        assertEquals(expected, actual)
    }
}