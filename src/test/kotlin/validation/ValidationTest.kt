package validation

import metaData.MetaDataReaderWriter
import org.json.JSONArray
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
            """[{"2":{"Dependency Error":["country name"],"Length Error":["product description","product id"],"Value Not Found Error":["source pincode"]}},{"3":{"Length Error":["product description","product id"]}},{"4":{"Dependency Error":["country name","country code"],"Length Error":["product description"],"Value Not Found Error":["source pincode"]}},{"5":{"Dependency Error":["country name","country code"],"Length Error":["product description"]}},{"6":{"Row Duplication Error":["4"],"Dependency Error":["country name","country code"],"Length Error":["product description"]}}]"""

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

    @ParameterizedTest
    @MethodSource("inValidDateTimeArguments")
    fun shouldGetValidationErrorForDate(path: String, csvData: String, expectedContent: String) {
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaDataTestFiles/date-time/$path")
        val validation = Validation(metaDataReaderWriter)
        val jsonData = JSONArray(csvData)

        val actual = validation.validate(jsonData)

        Assertions.assertEquals(expectedContent, actual.toString())
    }

    private fun inValidDateTimeArguments(): List<Arguments> {
        return listOf(
            Arguments.of(
                "date-meta-data-test.json",
                """[{"date": "11/02/2000",},{"date": "15/22/2002",},{"date": "15/02/23",}]""",
                """[{"3":{"Type Error":["date"]}},{"4":{"Type Error":["date"]}}]"""
            ),
            Arguments.of(
                "time-meta-data-test.json",
                """[{"time": "11:88:02 AM",},{"time": "11:23:02 AM",},{"time": "11:23:2 AM",}]""",
                """[{"2":{"Type Error":["time"]}},{"4":{"Type Error":["time"]}}]"""
            ),
            Arguments.of(
                "date-time-meta-data-test.json",
                """[{"datetime": "33:12:18 20/07/2000",},{"datetime": "00:12:18 20/77/2000",},{"datetime": "00:12:18 20/07/2000",}]""",
                """[{"2":{"Type Error":["datetime"]}},{"3":{"Type Error":["datetime"]}}]"""
            ),
        )
    }

}
