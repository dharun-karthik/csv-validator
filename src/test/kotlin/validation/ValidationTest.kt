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
    @MethodSource("inValidValidationArguments")
    fun shouldGetValidationErrorForDate(path: String, csvData: String, expectedContent: String) {
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaDataTestFiles/validation/$path")
        val validation = Validation(metaDataReaderWriter)
        val jsonData = JSONArray(csvData)

        val actual = validation.validate(jsonData)

        Assertions.assertEquals(expectedContent, actual.toString())
    }

    private fun inValidValidationArguments(): List<Arguments> {
        return listOf(
            Arguments.of(
                "date-meta-data-test.json",
                """[{"date": "11/02/2000",},{"date": "15/22/2002",},{"date": "15/02/23",}]""",
                """[{"3":{"Type Error":["15/22/2002"]}},{"4":{"Type Error":["15/02/23"]}}]"""
            ),
            Arguments.of(
                "length-meta-data-test.json",
                """[{"product description": "Table"}]""",
                """[{"2":{"Length Error":["Table"]}}]"""
            ),
            Arguments.of(
                "restricted-input-meta-data-test.json",
                """[{"export": "fa"}]""",
                """[{"2":{"Value Not Found Error":["fa"]}}]"""
            ),
            Arguments.of(
                "dependency-meta-data-test.json",
                """[{"export": "N","country name": "usa"}]""",
                """[{"2":{"Dependency Error":["not expected country name = hen, when export is N"]}}]"""
            ),
            Arguments.of(
                "restricted-input-meta-data-test.json",
                """[{"export": "N"},{"export": "N"}]""",
                """[{"3":{"Row Duplication Error":["2"]}}]"""
            ),
        )
    }

}
