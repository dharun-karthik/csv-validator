package routeHandler.post

import fakeStreams.FakeInputStreamProvider
import metaData.ConfigFileReaderWriter
import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ConfigWriterTest {

//    @Test
//    fun shouldBeAbleToWriteConfigToEmptyFile() {
//        val configFileReaderWriter =
//            ConfigFileReaderWriter("src/test/kotlin/metaDataTestFiles/configContent/new-json-test.json")
//        val post = ConfigWriter(configFileReaderWriter)
//        val data = """[
//  {
//    "fieldName": "ProductId",
//    "type": "AlphaNumeric",
//    "length": 5
//  },{
//    "fieldName": "Product Description",
//    "type": "AlphaNumeric",
//    "minLength": 7,
//    "maxLength": 20
//  }]"""
//
//        val fakeInputStreamProvider = FakeInputStreamProvider(data)
//        val request = """
//            Content-Length: ${data.length}
//
//        """
//        val response = post.uploadValidConfigJson(request, fakeInputStreamProvider)
//        println(response)
//        val fields = post.configFileReaderWriter.readFields()[0]
//        configFileReaderWriter.clearFields()
//
//        val expected = JsonConfigTemplate(
//            fieldName = "Product Description",
//            type = "AlphaNumeric",
//            minLength = 7,
//            maxLength = 20
//        )
//        assertEquals(expected, fields)
//    }

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