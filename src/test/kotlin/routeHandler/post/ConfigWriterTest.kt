package routeHandler.post

import metaData.template.JsonMetaDataTemplate
import metaData.ConfigReaderWriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import validation.implementation.FakeBufferedReader

class ConfigWriterTest {

    @Test
    fun shouldBeAbleToAppendCsvMetaDataToEmptyFile() {
        val configReaderWriter = ConfigReaderWriter("src/test/kotlin/metaDataTestFiles/new-json-test.json")
        val post = ConfigWriter(configReaderWriter)
        val data = """{
    "fieldName": "ProductId",
    "type": "AlphaNumeric",
    "length": 5
  }"""
        val fakeBufferedReader = FakeBufferedReader(data)
        val request = """
            Content-Length: ${data.length}
            
        """
        post.handleAddCsvMetaData(request, fakeBufferedReader)
        val fields = post.configReaderWriter.readFields()
        val actual = fields[0]
        configReaderWriter.clearFields()

        val expected =
            JsonMetaDataTemplate(fieldName = "ProductId", type = "AlphaNumeric", length = "5")

        assertEquals(expected, actual)
    }

    @Test
    fun shouldBeAbleToAppendCsvMetaData() {
        val configReaderWriter = ConfigReaderWriter("src/test/kotlin/metaDataTestFiles/append-json-test.json")
        val post = ConfigWriter(configReaderWriter)
        val arrayOfData = listOf(
            """
  {
    "fieldName": "Product Id",
    "type": "AlphaNumeric",
    "length": 5
  }
  """, """{
    "fieldName": "Product Description",
    "type": "AlphaNumeric",
    "minLength": 7,
    "maxLength": 20
  }"""
        )
        for (data in arrayOfData) {
            val request = """
            Content-Length: ${data.length}
            
        """
            val fakeBufferedReader = FakeBufferedReader(data)
            post.handleAddCsvMetaData(request, fakeBufferedReader)
        }

        val fields = post.configReaderWriter.readFields()
        val actual = fields[1]
        val expected = JsonMetaDataTemplate(
            fieldName = "Product Description",
            type = "AlphaNumeric",
            minLength = "7",
            maxLength = "20"
        )
        post.configReaderWriter.clearFields()

        assertEquals(expected, actual)
    }
}