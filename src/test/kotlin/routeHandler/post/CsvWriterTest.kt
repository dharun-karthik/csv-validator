package routeHandler.post

import metaData.JsonContentReaderWriter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import validation.implementation.FakeBufferedReader

class CsvWriterTest {

    @Test
    fun shouldBeAbleToGetEveryValidationErrorsFromTheJsonContent() {
        val jsonReaderWriter = JsonContentReaderWriter("src/test/kotlin/metaDataTestFiles/jsonContent/csv-content-test.json")
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
        val lineSeparator = System.lineSeparator()
        val content =
            """{"value":"Success"}"""
        val head = """HTTP/1.1 200 OK
Content-Type: application/json; charset=utf-8
Content-Length: ${content.length}"""
        val expectedContent = head + lineSeparator + lineSeparator + content
        val request = """
            Content-Length: ${csvData.length}
        """.trimIndent()
        val fakeBufferedReader = FakeBufferedReader(csvData)

        val actual = csvWriter.uploadCsvContent(request, fakeBufferedReader)

        Assertions.assertEquals(expectedContent, actual)
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

        Assertions.assertEquals(expectedContent, actual)
    }
     */
}