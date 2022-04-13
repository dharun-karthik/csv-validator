package validation

import metaData.MetaDataReaderWriter
import org.json.JSONArray
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ValidationTest {
    @Test
    fun shouldBeAbleToGetEveryValidationErrorsFromTheJsonContent() {
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaDataTestFiles/csv-meta-data-test.json")
        val validation = Validation(metaDataReaderWriter)
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
        "product id": "1234",
        "product description": "Chairs",
        "price": "1000",
        "export": "Y",
        "country name": "AUS",
        "source city": "Mumbai",
        "country code": "61",
        "source pincode": "500001"
    },
    {
        "product id": "12345",
        "product description": "Chairs",
        "price": "1000",
        "export": "N",
        "country name": "AUS",
        "source city": "Mumbai",
        "country code": "null",
        "source pincode": "400001"
    },
    {
        "product id": "12345",
        "product description": "Chairs",
        "price": "100",
        "export": "N",
        "country name": "USA",
        "source city": "Mumbai",
        "country code": "null",
        "source pincode": "700001"
    },
    {
        "product id": "12345",
        "product description": "Chairs",
        "price": "100",
        "export": "N",
        "country name": "USA",
        "source city": "Mumbai",
        "country code": "null",
        "source pincode": "700001"
    }
]"""
        val jsonData = JSONArray(csvData)
        val expectedContent =
            """[{"Line Number 1":"Dependency Error in country name"},{"Line Number 1":"Length Error in product description"},{"Line Number 1":"Foreign Value Found Error in source pincode"},{"Line Number 1":"Length Error in product id"},{"Line Number 2":"Length Error in product description"},{"Line Number 2":"Length Error in product id"},{"Line Number 3":"Dependency Error in country name"},{"Line Number 3":"Length Error in product description"},{"Line Number 3":"Foreign Value Found Error in source pincode"},{"Line Number 3":"Dependency Error in country code"},{"Line Number 4":"Dependency Error in country name"},{"Line Number 4":"Length Error in product description"},{"Line Number 4":"Dependency Error in country code"},{"Line Number 5":"Row Duplication From 4"},{"Line Number 5":"Dependency Error in country name"},{"Line Number 5":"Length Error in product description"},{"Line Number 5":"Dependency Error in country code"}]"""

        val actual = validation.validate(jsonData)

        Assertions.assertEquals(expectedContent, actual.toString())
    }

    @Test
    fun shouldGetEmptyJsonArrayWhenTheJsonContentIsValid() {
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaDataTestFiles/csv-meta-data-test.json")
        val validation = Validation(metaDataReaderWriter)
        val csvData = """[
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
        "product id": "12369",
        "product description": "wooden Chairs",
        "price": "1000",
        "export": "N",
        "country name": "null",
        "source city": "Mumbai",
        "country code": "null",
        "source pincode": "700001"
    }
]"""
        val jsonData = JSONArray(csvData)
        val expectedContent = "[]"

        val actual = validation.validate(jsonData)

        Assertions.assertEquals(expectedContent, actual.toString())
    }
}
