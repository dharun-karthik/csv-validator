package routeHandler.post

import metaData.ConfigReaderWriter
import metaData.template.JsonMetaDataTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import validation.implementation.FakeBufferedReader

class ConfigWriterTest {

    @Test
    fun shouldBeAbleToAppendCsvMetaDataToEmptyFile() {
        val configReaderWriter = ConfigReaderWriter("src/test/kotlin/metaDataTestFiles/new-json-test.json")
        val post = ConfigWriter(configReaderWriter)
        val data = """[
  {
    "fieldName": "ProductId",
    "type": "AlphaNumeric",
    "length": 5
  },{
    "fieldName": "Product Description",
    "type": "AlphaNumeric",
    "minLength": 7,
    "maxLength": 20
  }]"""

        val fakeBufferedReader = FakeBufferedReader(data)
        val request = """
            Content-Length: ${data.length}
            
        """
        post.handleWriteConfigData(request, fakeBufferedReader)
        val fields = post.configReaderWriter.readFields()[1]
        configReaderWriter.clearFields()

        val expected = JsonMetaDataTemplate(
            fieldName = "Product Description",
            type = "AlphaNumeric",
            minLength = "7",
            maxLength = "20"
        )
        assertEquals(expected, fields)
    }
}