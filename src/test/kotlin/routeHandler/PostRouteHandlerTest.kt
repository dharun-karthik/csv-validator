package routeHandler

import metaData.JsonMetaDataTemplate
import metaData.MetaDataReaderWriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import validation.FakeBufferedReader
import java.io.Reader

class PostRouteHandlerTest {

    @Test
    fun shouldBeAbleToAppendCsvMetaDataToEmptyFile() {
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaDataTestFiles/new-json-test.json")
        val post = PostRouteHandler(metaDataReaderWriter)
        val data = """{
    "fieldName": "ProductId",
    "type": "AlphaNumeric",
    "length": 5
  }"""
        post.addCsvMetaData(data)
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
        val post = PostRouteHandler(metaDataReaderWriter)
        val oldData = """
  {
    "fieldName": "Product Id",
    "type": "AlphaNumeric",
    "length": 5
  }
  """
        post.addCsvMetaData(oldData)
        val data = """{
    "fieldName": "Product Description",
    "type": "AlphaNumeric",
    "minLength": 7,
    "maxLength": 20
  }"""
        post.addCsvMetaData(data)

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