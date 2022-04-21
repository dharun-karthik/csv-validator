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

    private fun getBodyWithSuccessHeader(body: String): String {
        val lineBreak = System.lineSeparator()
        return """HTTP/1.1 200 OK
Content-Type: application/json; charset=utf-8
Content-Length: ${body.length}""" + lineBreak + lineBreak + body
    }

}