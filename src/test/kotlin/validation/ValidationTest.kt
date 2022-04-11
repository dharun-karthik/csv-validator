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
}
