package validation

import metaData.MetaDataReaderWriter
import org.json.JSONObject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import routeHandler.PostRouteHandler

//todo add more test cases
class ValidationTest {
    private val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaDataTestFiles/csv-meta-data-test.json")
    private val metaDataArray = metaDataReaderWriter.readFields()

    @Test
    fun shouldGiveLengthErrorLinesAsResult() {
        val validation = Validation(metaDataReaderWriter)
        val productIdField = metaDataArray[0]
        val actual = validation.lengthValidation(productIdField, "1564")

        assertTrue(actual)
    }

    @Test
    fun shouldGiveTypeErrorLinesAsResult() {
        val validation = Validation(metaDataReaderWriter)
        val priceField = metaDataArray[2]

        val actual = validation.typeValidation(priceField, "4500.59DD")

        assertTrue(actual)
    }

    @Test
    fun shouldGiveDependencyErrorLinesAsResult() {
        val validation = Validation(metaDataReaderWriter)
        val csvData = """{
            "Product Id": "1564",
            "Product Description": "Table",
            "Price": "4500.59DD",
            "Export": "N",
            "Country Name": "USA",
            "Source City": "Nagpur",
            "Country Code": "null",
            "Source Pincode": "440001"
        }"""
        val currentRow = JSONObject(csvData)
        val countryNameField = metaDataArray[4]

        val actual = validation.dependencyValidation(countryNameField, "USA",currentRow)

        assertTrue(actual)
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

        val fakeBufferedReader = FakeBufferedReader(csvData)
        val request = """
            Content-Length: ${csvData.length}
        """.trimIndent()
        val actual = postRouteHandler.handleCsv(request, fakeBufferedReader).split("\r\n\r\n")[1]

        Assertions.assertEquals(expectedContent, actual)
    }
}
