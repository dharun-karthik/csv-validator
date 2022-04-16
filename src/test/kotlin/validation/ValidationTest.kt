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
            """[{"1":["Dependency Error in country name","Length Error in product description","Value Not Found Error in source pincode","Length Error in product id"]},{"2":["Length Error in product description","Length Error in product id"]},{"3":["Dependency Error in country name","Length Error in product description","Value Not Found Error in source pincode","Dependency Error in country code"]},{"4":["Dependency Error in country name","Length Error in product description","Dependency Error in country code"]},{"5":["Row Duplication From 4","Dependency Error in country name","Length Error in product description","Dependency Error in country code"]}]"""

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
