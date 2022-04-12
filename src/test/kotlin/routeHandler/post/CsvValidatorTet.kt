package routeHandler.post

import metaData.MetaDataReaderWriter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import validation.FakeBufferedReader

class CsvValidatorTet {
    /*
    @Test
    fun shouldBeAbleToGetEveryValidationErrorsFromTheJsonContent() {
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaDataTestFiles/csv-meta-data-test.json")
        val csvValidator = CsvValidator(metaDataReaderWriter)
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
        "Product Id": "15645",
        "Product Description": "Table with flower",
        "Price": "4500.59",
        "Export": "Y",
        "Country Name": "USA",
        "Source City": "Nagpur",
        "Country Code": "08",
        "Source Pincode": "560001"
    },
    {
        "Product Id": "15645",
        "Product Description": "Table with flower",
        "Price": "4500.59",
        "Export": "Y",
        "Country Name": "USA",
        "Source City": "Nagpur",
        "Country Code": "08",
        "Source Pincode": "560001"
    }
]"""
        val head = """HTTP/1.1 200 OK
Content-Type: application/json; charset=utf-8
Content-Length: 916"""
        val lineSeparator = System.lineSeparator()
        val expectedContent = head + lineSeparator + lineSeparator +
            """[{"Line Number 5":"Row Duplicated From 4"},{"Line Number 1":"Length Error in Product Id"},{"Line Number 1":"Length Error in Product Description"},{"Line Number 2":"Length Error in Product Id"},{"Line Number 2":"Length Error in Product Description"},{"Line Number 3":"Length Error in Product Description"},{"Line Number 4":"Length Error in Product Description"},{"Line Number 5":"Length Error in Product Description"},{"Line Number 3":"Foreign Value Found Error in Source Pincode"},{"Line Number 5":"Foreign Value Found Error in Source Pincode"},{"Line Number 1":"Dependency Error in Country Name"},{"Line Number 3":"Dependency Error in Country Name"},{"Line Number 3":"Dependency Error in Country Code"},{"Line Number 4":"Dependency Error in Country Name"},{"Line Number 4":"Dependency Error in Country Code"},{"Line Number 5":"Dependency Error in Country Name"},{"Line Number 5":"Dependency Error in Country Code"}]"""
        val request = """
            Content-Length: ${csvData.length}
        """.trimIndent()
        val fakeBufferedReader = FakeBufferedReader(csvData)

        val actual = csvValidator.handleCsv(request, fakeBufferedReader)
        println(actual)

        Assertions.assertEquals(expectedContent, actual)
    }

     */

    @Test
    fun shouldGetColumnErrorWhenInvalidColumnNameIsGiven() {
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaDataTestFiles/csv-meta-data-test.json")
        val csvValidator = CsvValidator(metaDataReaderWriter)
        val csvData =  """[
    {
        "ProducId": "1564",
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
        "Source Pincode": "500001",
        "Sourcekfja": "500001"
        
    }
]"""

        val head = """HTTP/1.1 200 OK
Content-Type: application/json; charset=utf-8
Content-Length: 916"""
        val lineSeparator = System.lineSeparator()
        val expectedContent = head + lineSeparator + lineSeparator +
            """[{"Column unavailable in config":"producid"},{"Column unavailable in config":"Sourcekfja"}]"""
        val request = """
            Content-Length: ${csvData.length}
        """.trimIndent()
        val fakeBufferedReader = FakeBufferedReader(csvData)

        val actual = csvValidator.handleCsv(request, fakeBufferedReader)
        println(actual)

        Assertions.assertEquals(expectedContent, actual)
    }
}