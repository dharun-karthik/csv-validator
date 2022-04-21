package routeHandler.get

import metaData.ConfigFileReaderWriter
import metaData.JsonContentReaderWriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CsvValidatorTest {

    @Test
    fun shouldGetColumnErrorWhenInvalidColumnNameIsGiven() {
        val configFileReaderWriter =
            ConfigFileReaderWriter("src/test/kotlin/metaDataTestFiles/csvValidation/csv-config-test.json")
        val jsonContentReaderWriter =
            JsonContentReaderWriter("src/test/kotlin/metaDataTestFiles/csvValidation/content-with-wrong-column-name-test.json")
        val csvValidator = CsvValidator(configFileReaderWriter, jsonContentReaderWriter)

        val actual = csvValidator.handleCsv()
        val content = """[{"0":["producid"]}]"""
        val expected = getBodyWithSuccessHeader(content)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnErrorValuesWhenTheValidationFails(){
        val configFileReaderWriter =
            ConfigFileReaderWriter("src/test/kotlin/metaDataTestFiles/csvValidation/csv-config-test.json")
        val jsonContentReaderWriter =
            JsonContentReaderWriter("src/test/kotlin/metaDataTestFiles/csvValidation/content-with-error-test.json")
        val csvValidator = CsvValidator(configFileReaderWriter, jsonContentReaderWriter)

        val actual = csvValidator.handleCsv()
        val content = """[{"2":{"Length Error in":["product description : Table"],"Value Not Found":["source pincode : 560002"]}}]"""
        val expected = getBodyWithSuccessHeader(content)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnEmptyArrayWhenValidationIsSuccess(){
        val configFileReaderWriter =
            ConfigFileReaderWriter("src/test/kotlin/metaDataTestFiles/csvValidation/csv-config-test.json")
        val jsonContentReaderWriter =
            JsonContentReaderWriter("src/test/kotlin/metaDataTestFiles/csvValidation/content-with-no-error-test.json")
        val csvValidator = CsvValidator(configFileReaderWriter, jsonContentReaderWriter)

        val actual = csvValidator.handleCsv()
        val content = """[]"""
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