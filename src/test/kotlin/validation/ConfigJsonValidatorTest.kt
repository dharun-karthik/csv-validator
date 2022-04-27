package validation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ConfigJsonValidatorTest {

    @Test
    fun shouldReturnNegativeLengthErrorFieldNames() {
        val configJsonValidator = ConfigJsonValidator("src/test/kotlin/metaDataTestFiles/configValidation/fixed-len-error.json")
        val expected = """[{"Fixed len less than one":["price"]},{"Min length less than one":[]},{"Max length less than one":[]},{"Min length greater than max length":[]}]"""

        val actual = configJsonValidator.validate().toString()

        assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnMinAndMaxLengthErrorFieldNames() {
        val configJsonValidator = ConfigJsonValidator("src/test/kotlin/metaDataTestFiles/configValidation/min-max-len-error.json")
        val expected = """[{"Fixed len less than one":[]},{"Min length less than one":[]},{"Max length less than one":[]},{"Min length greater than max length":["country code"]}]"""

        val actual = configJsonValidator.validate().toString()

        assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnMinLengthErrorFieldNames() {
        val configJsonValidator = ConfigJsonValidator("src/test/kotlin/metaDataTestFiles/configValidation/min-len-error.json")
        val expected = """[{"Fixed len less than one":[]},{"Min length less than one":["export"]},{"Max length less than one":[]},{"Min length greater than max length":[]}]"""

        val actual = configJsonValidator.validate().toString()

        assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnMaxLengthErrorFieldNames() {
        val configJsonValidator = ConfigJsonValidator("src/test/kotlin/metaDataTestFiles/configValidation/max-len-error.json")
        val expected = """[{"Fixed len less than one":[]},{"Min length less than one":[]},{"Max length less than one":["export"]},{"Min length greater than max length":[]}]"""

        val actual = configJsonValidator.validate().toString()

        assertEquals(expected, actual)
    }

}