package routeHandler.post

import fakeStreams.FakeInputStreamProvider
import metaData.ConfigFileReaderWriter
import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ConfigWriterTest {
    @Test
    fun shouldNotWriteConfigIfValidationFails() {
        val configFileReaderWriter =
            ConfigFileReaderWriter("src/test/kotlin/metaDataTestFiles/configContent/should-not-write-config-test.json")
        val post = ConfigWriter(configFileReaderWriter)
        val data = """[
  {
    "type": "AlphaNumeric",
    "length": 5
  },{
    "fieldName": "Product Description",
    "minLength": 20,
    "maxLength": 7
  }]"""

        val fakeInputStreamProvider = FakeInputStreamProvider(data)
        val request = """
            Content-Length: ${data.length}
            
        """
        post.uploadValidConfigJson(request, fakeInputStreamProvider)
        val fields = post.configFileReaderWriter.readFields()

        assertTrue(fields.isEmpty())
    }

    @Test
    fun shouldGetResponseIfValidationFails() {
        val configFileReaderWriter =
            ConfigFileReaderWriter("src/test/kotlin/metaDataTestFiles/configContent/should-not-write-config-test.json")
        val post = ConfigWriter(configFileReaderWriter)
        val data = """[
  {
    "type": "alphanumeric",
    "length": 5
  },{
    "fieldName": "Product Description",
    "minLength": 20,
    "maxLength": 7
  }]"""

        val fakeInputStreamProvider = FakeInputStreamProvider(data)
        val request = """
            Content-Length: ${data.length}
            
        """
        val content =
            """[{"1":[{"Field errors":["Field 'fieldName' should be provided"]}]},{"2":[{"Field errors":["Field 'type' should be provided","<empty> is not supported","Max length : 7 should be greater than min length : 20"]}]}]"""
        val expected = """HTTP/1.1 400 Bad Request
            |Content-Type: application/json; charset=utf-8
            |Content-Length: ${content.length}
            |
            |$content""".trimMargin()

        val actual = post.uploadValidConfigJson(request, fakeInputStreamProvider)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldNotWriteConfigIfValuesAreEmpty() {
        val configFileReaderWriter =
            ConfigFileReaderWriter("src/test/kotlin/metaDataTestFiles/configContent/empty-config-write-test.json")
        val post = ConfigWriter(configFileReaderWriter)
        val data = """[]"""

        val fakeInputStreamProvider = FakeInputStreamProvider(data)
        val request = """
            Content-Length: ${data.length}
            
        """
        post.uploadValidConfigJson(request, fakeInputStreamProvider)
        val fields = post.configFileReaderWriter.readFields()

        assertTrue(fields.isEmpty())
    }
}