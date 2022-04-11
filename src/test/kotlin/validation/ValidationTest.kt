package validation

import metaData.MetaDataReaderWriter
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

//todo add more test cases
class ValidationTest {
    private val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaDataTestFiles/csv-meta-data-test.json")
    private val metaDataArray = metaDataReaderWriter.readFields()

    @Test
    fun shouldGiveLengthErrorLinesAsResult() {
        val validation = Validation(metaDataReaderWriter)
        val csvData = """{
            "Product Id": "1564",
            "Product Description": "Table",
            "Price": "4500.59",
            "Export": "N",
            "Country Name": "null",
            "Source City": "Nagpur",
            "Country Code": "null",
            "Source Pincode": "440001"
        }"""
        val currentRow = JSONObject(csvData)
        val fieldName = "Product Id"

        val actual = validation.lengthValidation(metaDataArray, fieldName,currentRow)

        assertTrue(actual)
    }

    @Test
    fun shouldGiveTypeErrorLinesAsResult() {
        val validation = Validation(metaDataReaderWriter)
        val csvData = """{
            "Product Id": "1564",
            "Product Description": "Table",
            "Price": "4500.59DD",
            "Export": "N",
            "Country Name": "null",
            "Source City": "Nagpur",
            "Country Code": "null",
            "Source Pincode": "440001"
        }"""
        val currentRow = JSONObject(csvData)
        val fieldName = "Price"

        val actual = validation.typeValidation(metaDataArray, fieldName,currentRow)

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
        val fieldName = "Country Name"

        val actual = validation.dependencyValidation(metaDataArray, fieldName,currentRow)

        assertTrue(actual)
    }
}
