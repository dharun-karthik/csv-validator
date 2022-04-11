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

    @Test
    fun shouldBeAbleToGetEveryValidationErrorsFromTheJsonContent() {
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaDataTestFiles/csv-meta-data-test.json")
        val postRouteHandler = PostRouteHandler(metaDataReaderWriter)
        val csvData = """[
    {
        "Product Id": "1564",
        "Product Description": "Table",
        "Price": "4500.59",
        "Export": "Y",
        "Country Name": "null",
        "Source City": "Nagpur",
        "Country Code": "null",
        "Source Pincode": "440001"
    },
    {
        "Product Id": "1234",
        "Product Description": "Chairs",
        "Price": "1000",
        "Export": "Y",
        "Country Name": "AUS",
        "Source City": "Mumbai",
        "Country Code": "61",
        "Source Pincode": "400001"
    },
    {
        "Product Id": "12345",
        "Product Description": "Chairs",
        "Price": "1000",
        "Export": "N",
        "Country Name": "AUS",
        "Source City": "Mumbai",
        "Country Code": "null",
        "Source Pincode": "400001"
    },
    {
        "Product Id": "12345",
        "Product Description": "Chairs",
        "Price": "100",
        "Export": "N",
        "Country Name": "USA",
        "Source City": "Mumbai",
        "Country Code": "null",
        "Source Pincode": "400001"
    },
    {
        "Product Id": "12345",
        "Product Description": "Chairs",
        "Price": "100",
        "Export": "N",
        "Country Name": "USA",
        "Source City": "Mumbai",
        "Country Code": "null",
        "Source Pincode": "400001"
    }
]"""
        val expectedContent =
            """[{"5":"Row Duplicated From 4"},{"1":"Length Error in Product Id"},{"1":"Length Error in Country Code"},{"1":"Length Error in Product Description"},{"2":"Length Error in Product Id"},{"2":"Length Error in Product Description"},{"3":"Length Error in Country Code"},{"3":"Length Error in Product Description"},{"4":"Length Error in Country Code"},{"4":"Length Error in Product Description"},{"5":"Length Error in Country Code"},{"5":"Length Error in Product Description"},{"1":"Dependency Error in Country Name"},{"3":"Dependency Error in Country Name"},{"3":"Dependency Error in Country Code"},{"4":"Dependency Error in Country Name"},{"4":"Dependency Error in Country Code"},{"5":"Dependency Error in Country Name"},{"5":"Dependency Error in Country Code"}]"""

        val fakeBufferedReader = FakeBufferedReader(Reader.nullReader(), csvData)
        val request = """
            Content-Length: ${csvData.length}
        """.trimIndent()
        val actual = postRouteHandler.handleCsv(request, fakeBufferedReader).split("\r\n\r\n")[1]

        assertEquals(expectedContent, actual)
    }

}