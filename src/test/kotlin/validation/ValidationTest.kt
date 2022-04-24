package validation

import metaData.ConfigFileReaderWriter
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
        val configFileReaderWriter =
            ConfigFileReaderWriter("src/test/kotlin/metaDataTestFiles/configContent/csv-config-content-test.json")
        val validation = Validation(configFileReaderWriter)
        val csvData = """[
    {
        "product id": "15645",
        "product description": "TableIsT",
        "price": "4500.59",
        "export": "Y",
        "country name": "USA",
        "source city": "Nagpur",
        "country code": "08",
        "source pincode": "560001"
    },
    {
        "product id": "12369",
        "product description": "woodenIs",
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

    @Test
    fun shouldGetEmptyJsonArrayWhenThereIsNoContent() {
        val configFileReaderWriter =
            ConfigFileReaderWriter("src/test/kotlin/metaDataTestFiles/configContent/csv-config-content-test.json")
        val validation = Validation(configFileReaderWriter)
        val csvData = """[]"""
        val jsonData = JSONArray(csvData)
        val expectedContent = "[]"

        val actual = validation.validate(jsonData)

        Assertions.assertEquals(expectedContent, actual.toString())
    }

    @ParameterizedTest
    @MethodSource("inValidValidationArguments")
    fun shouldGetValidationErrorForDate(path: String, csvData: String, expectedContent: String) {
        val configFileReaderWriter = ConfigFileReaderWriter("src/test/kotlin/metaDataTestFiles/validation/$path")
        val validation = Validation(configFileReaderWriter)
        val jsonData = JSONArray(csvData)

        val actual = validation.validate(jsonData)

        Assertions.assertEquals(expectedContent, actual.toString())
    }

    private fun inValidValidationArguments(): List<Arguments> {
        return listOf(
            Arguments.of(
                "date-meta-data-test.json",
                """[{"date": "11/02/2000",},{"date": "15/22/2002",},{"date": "15/02/23",}]""",
                """[{"3":{"Type expected 'date' in":["date : 15/22/2002"]}},{"4":{"Type expected 'date' in":["date : 15/02/23"]}}]"""
            ),
            Arguments.of(
                "length-meta-data-test.json",
                """[{"product description": "Table"}]""",
                """[{"2":{"Length Error in":["product description : Table"]}}]"""
            ),
            Arguments.of(
                "restricted-input-meta-data-test.json",
                """[{"export": "fa"}]""",
                """[{"2":{"Value Not Found":["export : fa"]}}]"""
            ),
            Arguments.of(
                "dependency-meta-data-test.json",
                """[{"export": "N","country name": "usa"}]""",
                """[{"2":{"export is N but":["country name : usa"]}}]"""
            ),
            Arguments.of(
                "restricted-input-meta-data-test.json",
                """[{"export": "N"},{"export": "N"}]""",
                """[{"3":{"Row Duplication":["2"]}}]"""
            ),
            Arguments.of(
                "email-meta-data-test.json",
                """[{"email": "talon.atlas+managedsahaj.ai"}]""",
                """[{"2":{"Type expected 'email' in":["email : talon.atlas+managedsahaj.ai"]}}]"""
            ),
            Arguments.of(
                "text-meta-data-test.json",
                """[{"text": "£ new one"}]""",
                """[{"2":{"Type expected 'text' in":["text : £ new one"]}}]"""
            ),
            Arguments.of(
                "null-meta-data-test.json",
                """[{"name": "john"},{"name":"null"}]""",
                """[{"3":{"Empty Value not allowed in":["name : null"]}}]"""
            )
        )
    }

}
