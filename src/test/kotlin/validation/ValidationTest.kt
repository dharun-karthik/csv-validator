package validation

import metaData.MetaDataReaderWriter
import org.json.JSONArray
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

//todo add more test cases
class ValidationTest {
    @Test
    fun shouldBeAbleToGetEveryValidationErrorsFromTheJsonContent() {
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaDataTestFiles/csv-meta-data-test.json")
        val validation = Validation(metaDataReaderWriter)
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
        "Source Pincode": "500001"
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
        "Source Pincode": "700001"
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
        val jsonData = JSONArray(csvData)
        val expectedContent =
            """[{"Line Number 1":"Length Error in Product Id"},{"Line Number 1":"Length Error in Product Description"},{"Line Number 2":"Length Error in Product Id"},{"Line Number 2":"Length Error in Product Description"},{"Line Number 3":"Length Error in Product Description"},{"Line Number 4":"Length Error in Product Description"},{"Line Number 5":"Length Error in Product Description"},{"Line Number 1":"Foreign Value Found Error in Source Pincode"},{"Line Number 3":"Foreign Value Found Error in Source Pincode"},{"Line Number 5":"Foreign Value Found Error in Source Pincode"},{"Line Number 1":"Dependency Error in Country Name"},{"Line Number 3":"Dependency Error in Country Name"},{"Line Number 3":"Dependency Error in Country Code"},{"Line Number 4":"Dependency Error in Country Name"},{"Line Number 4":"Dependency Error in Country Code"},{"Line Number 5":"Dependency Error in Country Name"},{"Line Number 5":"Dependency Error in Country Code"}]"""

        val actual = validation.validate(jsonData)

        Assertions.assertEquals(expectedContent, actual.toString())
    }

    @Test
    fun shouldGetEmptyJsonArrayWhenTheJsonContentIsValid(){
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaDataTestFiles/csv-meta-data-test.json")
        val validation = Validation(metaDataReaderWriter)
        val csvData = """[
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
        "Product Id": "12369",
        "Product Description": "wooden Chairs",
        "Price": "1000",
        "Export": "N",
        "Country Name": "null",
        "Source City": "Mumbai",
        "Country Code": "null",
        "Source Pincode": "700001"
    }
]"""
        val jsonData = JSONArray(csvData)
        val expectedContent = "[]"

        val actual = validation.validate(jsonData)

        Assertions.assertEquals(expectedContent, actual.toString())
    }
}
