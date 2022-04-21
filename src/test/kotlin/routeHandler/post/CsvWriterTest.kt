package routeHandler.post

import metaData.JsonContentReaderWriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import validation.implementation.FakeBufferedReader

class CsvWriterTest {

    @Test
    fun shouldBeAbleToGetEveryValidationErrorsFromTheJsonContent() {
        val jsonReaderWriter =
            JsonContentReaderWriter("src/test/kotlin/metaDataTestFiles/jsonContent/csv-content-test.json")

        val actual = sendCsvData(jsonReaderWriter)
        val lineSeparator = System.lineSeparator()
        val content = """{"value":"Success"}"""
        val head = """HTTP/1.1 200 OK
Content-Type: application/json; charset=utf-8
Content-Length: ${content.length}"""
        val expectedContent = head + lineSeparator + lineSeparator + content

        assertEquals(expectedContent, actual)
    }

    @Test
    fun shouldWriteJsonContentToTheFile() {
        val jsonReaderWriter =
            JsonContentReaderWriter("src/test/kotlin/metaDataTestFiles/jsonContent/csv-content-test.json")

        sendCsvData(jsonReaderWriter)

        val actual = jsonReaderWriter.readRawContent()
        val expected = """[
    {
        "product id": "1564",
        "product description": "Table"
    },
    {
        "product id": "15293",
        "product description": "Red Chairs"
     }
]"""
        assertEquals(expected, actual)
    }

    private fun sendCsvData(jsonReaderWriter: JsonContentReaderWriter): String {
        val csvWriter = CsvWriter(jsonReaderWriter)
        val csvData = """[
    {
        "product id": "1564",
        "product description": "Table"
    },
    {
        "product id": "15293",
        "product description": "Red Chairs"
     }
]"""
        val request = """
            Content-Length: ${csvData.length}
        """.trimIndent()
        val fakeBufferedReader = FakeBufferedReader(csvData)
        return csvWriter.uploadCsvContent(request, fakeBufferedReader)

    }

    /*
    @Test
    fun shouldGetColumnErrorWhenInvalidColumnNameIsGiven() {
        val configReaderWriter = ConfigReaderWriter("src/test/kotlin/metaDataTestFiles/configContent/csv-config-content-test.json")
        val csvValidator = CsvValidator(configReaderWriter)
        val csvData = """[
    {
        "Producid": "1564",
        "product description": "Table",
        "price": "4500.59",
        "export": "Y",
        "country name": "null",
        "source city": "Nagpur",
        "country code": "null",
        "source pincode": "440001",
        "Sourcekfja": "500001"
    }
]"""

        val head = """HTTP/1.1 200 OK
Content-Type: application/json; charset=utf-8
Content-Length: 33"""
        val lineSeparator = System.lineSeparator()
        val expectedContent = head + lineSeparator + lineSeparator +
                """[{"0":["sourcekfja","producid"]}]"""
        val request = """
            Content-Length: ${csvData.length}
        """.trimIndent()
        val fakeBufferedReader = FakeBufferedReader(csvData)

        val actual = csvValidator.uploadCsvContent(request, fakeBufferedReader)
        println(actual)

        assertEquals(expectedContent, actual)
    }
     */
}