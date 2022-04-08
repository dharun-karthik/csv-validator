package routeHandler

import metaData.JsonMetaDataTemplate
import metaData.MetaDataReaderWriter
import org.json.JSONArray
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PostTest {

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

        val expected = JsonMetaDataTemplate("ProductId", "AlphaNumeric", 5, null, null, null)

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

        val expected = JsonMetaDataTemplate("Product Description", "AlphaNumeric", null, 7, 20, null)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldGiveTypeErrorLinesAsResult() {
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaDataTestFiles/csv-meta-data-test.json")
        val postRouteHandler = PostRouteHandler(metaDataReaderWriter)
        val csvData = """[
    {
        "Product Id": "1564",
        "Product Description": "Table",
        "Price": "4500.59d",
        "Export": "N",
        "Source City": "Nagpur",
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
    }
]"""
        val jsonCsvData = JSONArray(csvData)
        val expected = mutableListOf(1)
        val result = postRouteHandler.typeValidation(jsonCsvData)
        assertEquals(expected, result)
    }

    @Test
    fun shouldGiveLengthErrorLinesAsResult() {
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaDataTestFiles/csv-meta-data-test.json")
        val postRouteHandler = PostRouteHandler(metaDataReaderWriter)
        val csvData = """[
    {
        "Product Id": "1564",
        "Product Description": "Table",
        "Price": "4500.59",
        "Export": "N",
        "Source City": "Nagpur",
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
    }
]"""
        val jsonCsvData = JSONArray(csvData)
        val expected = mutableListOf(1, 2)
        val result = postRouteHandler.lengthValidation(jsonCsvData)
        assertEquals(expected, result)
    }
}
