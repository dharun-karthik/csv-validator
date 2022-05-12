package routeHandler.get

import metaData.ConfigFileReaderWriter
import metaData.csv.CsvContentReader
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CsvValidatorTest {

    @Test
    fun shouldGetColumnErrorWhenInvalidColumnNameIsGiven() {
        val configFileReaderWriter =
            ConfigFileReaderWriter("src/test/kotlin/metaDataTestFiles/csvValidation/csv-config-test.json")
        val csvContentReader =
            CsvContentReader("src/test/kotlin/metaDataTestFiles/csvValidation/content-with-wrong-column-name-test.csv")
        val csvValidator = CsvValidator(configFileReaderWriter, csvContentReader)

        val actual = csvValidator.handleCsv()
        val content = """[{"0":["producid"]}]"""
        val expected = getBodyWithSuccessHeader(content)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnErrorValuesWhenTheValidationFails() {
        val configFileReaderWriter =
            ConfigFileReaderWriter("src/test/kotlin/metaDataTestFiles/csvValidation/csv-config-test.json")
        val csvContentReader =
            CsvContentReader("src/test/kotlin/metaDataTestFiles/csvValidation/content-with-error-test.csv")
        val csvValidator = CsvValidator(configFileReaderWriter, csvContentReader)

        val actual = csvValidator.handleCsv()
        val content =
            """{"country name":{"total-error-count":0,"details":{}},"product description":{"total-error-count":1,"details":{"Length error":{"error-count":1,"lines":{"1":"Value length should be lesser than 7 in product description : Table"}}}},"source city":{"total-error-count":0,"details":{}},"source pincode":{"total-error-count":1,"details":{"Value not found":{"error-count":1,"lines":{"1":"Value Not Found source pincode : 560002"}}}},"price":{"total-error-count":0,"details":{}},"product id":{"total-error-count":0,"details":{}},"country code":{"total-error-count":0,"details":{}},"export":{"total-error-count":0,"details":{}}}"""
        val expected = getBodyWithSuccessHeader(content)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnEmptyArrayWhenValidationIsSuccess() {
        val configFileReaderWriter =
            ConfigFileReaderWriter("src/test/kotlin/metaDataTestFiles/csvValidation/csv-config-test.json")
        val csvContentReader =
            CsvContentReader("src/test/kotlin/metaDataTestFiles/csvValidation/content-with-no-error-test.csv")
        val csvValidator = CsvValidator(configFileReaderWriter, csvContentReader)

        val actual = csvValidator.handleCsv()
        val content =
            """{"country name":{"total-error-count":0,"details":{}},"product description":{"total-error-count":0,"details":{}},"source city":{"total-error-count":0,"details":{}},"source pincode":{"total-error-count":0,"details":{}},"price":{"total-error-count":0,"details":{}},"product id":{"total-error-count":0,"details":{}},"country code":{"total-error-count":0,"details":{}},"export":{"total-error-count":0,"details":{}}}"""
        val expected = getBodyWithSuccessHeader(content)

        assertEquals(expected, actual)
    }


    private fun getBodyWithSuccessHeader(body: String): String {
        val lineBreak = System.lineSeparator()
        return """HTTP/1.1 200 OK
Content-Type: application/json; charset=utf-8
Content-Length: ${body.length}""" + lineBreak + lineBreak + body
    }

}