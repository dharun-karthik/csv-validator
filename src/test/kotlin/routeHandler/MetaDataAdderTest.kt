package routeHandler

import metaData.JsonMetaDataTemplate
import metaData.MetaDataReaderWriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import routeHandler.post.MetaDataAdder
import validation.FakeBufferedReader

class MetaDataAdderTest {

    @Test
    fun shouldBeAbleToAppendCsvMetaDataToEmptyFile() {
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaDataTestFiles/new-json-test.json")
        val post = MetaDataAdder(metaDataReaderWriter)
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
        val fields = post.metaDataReaderWriter.readFields()
        val actual = fields[0]
        metaDataReaderWriter.clearFields()

        val expected =
            JsonMetaDataTemplate(fieldName = "ProductId", type = "AlphaNumeric", length = "5")

        assertEquals(expected, actual)
    }

    @Test
    fun shouldBeAbleToAppendCsvMetaData() {
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaDataTestFiles/append-json-test.json")
        val post = MetaDataAdder(metaDataReaderWriter)
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
        for(data in arrayOfData) {
            val request = """
            Content-Length: ${data.length}
            
        """
            val fakeBufferedReader = FakeBufferedReader(data)
            post.handleAddCsvMetaData(request, fakeBufferedReader)
        }

        val fields = post.metaDataReaderWriter.readFields()
        val actual = fields[1]
        val expected = JsonMetaDataTemplate(
            fieldName = "Product Description",
            type = "AlphaNumeric",
            minLength = "7",
            maxLength = "20"
        )
        post.metaDataReaderWriter.clearFields()

        assertEquals(expected, actual)
    }
}