package metaData

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FileReaderWriterTest {
    @Test
    fun shouldBeAbleToReadRawContent() {
        val fileReaderWriter = FileReaderWriter("src/test/kotlin/metaDataTestFiles/csv-meta-data-test.json")
        val expected = """[
  {
    "fieldName": "product id",
    "type": "alphanumeric",
    "length": 5
  },
  {
    "fieldName": "product description",
    "type": "alphanumeric",
    "minLength": 7,
    "maxLength": 20
  },
  {
    "fieldName": "price",
    "type": "number"
  },
  {
    "fieldName": "export",
    "type": "alphabets",
    "values": [
      "Y",
      "N"
    ]
  },
  {
    "fieldName": "country name",
    "type": "alphabets",
    "minLength": 3,
    "dependencies": [
      {
        "dependentOn": "export",
        "expectedDependentFieldValue": "N",
        "expectedCurrentFieldValue": "null"
      },
      {
        "dependentOn": "export",
        "expectedDependentFieldValue": "Y",
        "expectedCurrentFieldValue": "!null"
      }
    ]
  },
  {
    "fieldName": "source city",
    "type": "alphabets",
    "minLength": 3
  },
  {
    "fieldName": "country code",
    "type": "number",
    "maxLength": 3,
    "dependencies": [
      {
        "dependentOn": "country name",
        "expectedDependentFieldValue": "null",
        "expectedCurrentFieldValue": "null"
      },
      {
        "dependentOn": "country name",
        "expectedDependentFieldValue": "!null",
        "expectedCurrentFieldValue": "!null"
      }
    ]
  },
  {
    "fieldName": "source pincode",
    "type": "number",
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

        val actual = fileReaderWriter.readRawContent()

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun shouldBeAbleToWriteRawContent(){
        val fileReaderWriter = FileReaderWriter("src/test/kotlin/metaDataTestFiles/write-content-test.json")
        val data = """[{"hello":"123"}]"""

        fileReaderWriter.writeRawContent(data)
        val actual = fileReaderWriter.readRawContent()

        Assertions.assertEquals(data, actual)
    }
}