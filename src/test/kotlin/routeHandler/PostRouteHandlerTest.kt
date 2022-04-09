package routeHandler

import metaData.JsonMetaDataTemplate
import metaData.MetaDataReaderWriter
import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

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
            JsonMetaDataTemplate(fieldName = "ProductId", type = "AlphaNumeric", length = 5)

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
            minLength = 7,
            maxLength = 20
        )

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

        val expected = JSONArray()
        expected.put(JSONObject().put("1","Type Error in Price"))
        val result = postRouteHandler.typeValidation(jsonCsvData)

        assertEquals(expected.toString(), result.toString())
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
        val expected = JSONArray()
        expected.put(JSONObject().put("1","Length Error in Product Id"))
        expected.put(JSONObject().put("2","Length Error in Product Id"))
        val result = postRouteHandler.lengthValidation(jsonCsvData)

        assertEquals(expected.toString(), result.toString())
    }

    @Test
    fun shouldGiveDependencyErrorLinesAsResult(){
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaDataTestFiles/csv-meta-data-test.json")
        val postRouteHandler = PostRouteHandler(metaDataReaderWriter)
        val csvData = """[
    {
        "Product Id": "1564",
        "Product Description": "Table",
        "Price": "4500.59",
        "Export": "N",
        "Country Name","AUS",
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
        val expected = JSONArray()
        expected.put(JSONObject().put("1","Dependency Error in Country Name"))
        val result = postRouteHandler.dependencyValidation(jsonCsvData)

        assertEquals(expected.toString(), result.toString())
    }

}