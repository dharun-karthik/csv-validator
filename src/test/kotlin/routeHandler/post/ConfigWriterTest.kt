package routeHandler.post

import fakeStreams.FakeInputStreamProvider
import metaData.ConfigFileReaderWriter
import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConfigWriterTest {

    @Test
    fun shouldBeAbleToWriteConfigToEmptyFile() {
        val configFileReaderWriter =
            ConfigFileReaderWriter("src/test/kotlin/metaDataTestFiles/configContent/new-json-test.json")
        val post = ConfigWriter(configFileReaderWriter)
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

        val fakeInputStreamProvider = FakeInputStreamProvider(data)
        val request = """
            Content-Length: ${data.length}
            
        """
        val response = post.uploadValidConfigJson(request, fakeInputStreamProvider)
        println(response)
        val fields = post.configFileReaderWriter.readFields()[1]
        configFileReaderWriter.clearFields()

        val expected = JsonConfigTemplate(
            fieldName = "Product Description",
            type = "AlphaNumeric",
            minLength = 7,
            maxLength = 20
        )
        assertEquals(expected, fields)
    }
}