package routeHandler.post

import metaData.MetaDataReaderWriter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import validation.implementation.FakeBufferedReader

class CsvValidatorTest {

    @Test
    fun shouldBeAbleToGetEveryValidationErrorsFromTheJsonContent() {
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaDataTestFiles/csv-meta-data-test.json")
        val csvValidator = CsvValidator(metaDataReaderWriter)
        val csvData = """[
    {
        "product id": "1564",
        "product description": "Table",
        "price": "4500.59",
        "export": "Y",
        "country name": "null",
        "source city": "Nagpur",
        "country code": "null",
        "source pincode": "440001"
    },
    {
        "product id": "15645",
        "product description": "Table with flower",
        "price": "4500.59",
        "export": "Y",
        "country name": "USA",
        "source city": "Nagpur",
        "country code": "08",
        "source pincode": "560001"
    },
    {
        "product id": "15645",
        "product description": "Table with flower",
        "price": "4500.59",
        "export": "Y",
        "country name": "USA",
        "source city": "Nagpur",
        "country code": "08",
        "source pincode": "560001"
    }
]"""
        val lineSeparator = System.lineSeparator()
        val content =
            """[{"1":{"Dependency Error":["country name"],"Length Error":["product description","product id"],"Value Not Found Error":["source pincode"]}},{"3":{"Row Duplication Error":["2"]}}]"""
        val head = """HTTP/1.1 200 OK
Content-Type: application/json; charset=utf-8
Content-Length: ${content.length}"""
        val expectedContent = head + lineSeparator + lineSeparator + content
        val request = """
            Content-Length: ${csvData.length}
        """.trimIndent()
        val fakeBufferedReader = FakeBufferedReader(csvData)

        val actual = csvValidator.handleCsv(request, fakeBufferedReader)

        Assertions.assertEquals(expectedContent, actual)
    }

    @Test
    fun shouldGetColumnErrorWhenInvalidColumnNameIsGiven() {
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaDataTestFiles/csv-meta-data-test.json")
        val csvValidator = CsvValidator(metaDataReaderWriter)
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

        val actual = csvValidator.handleCsv(request, fakeBufferedReader)
        println(actual)

        Assertions.assertEquals(expectedContent, actual)
    }
}