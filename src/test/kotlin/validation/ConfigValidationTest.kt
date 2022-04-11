package validation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConfigValidationTest {

    @Test
    fun shouldReturnEmptyJsonArrayIfAllConfigColumnsArePresentInCsvData() {
        val configJSON =
            "[{\"fieldName\":\"ProductID\",\"type\":\"Number\",\"length\":\"\",\"minLength\":\"\",\"maxLength\":\"\"},{\"fieldName\":\"ProductName\",\"type\":\"Alphabet\",\"length\":\"\",\"minLength\":\"\",\"maxLength\":\"\"}]"
        val jsonData =
            "[{\"ProductID\":\"1564\",\"ProductName\":\"Table\",\"Price\":\"4500.59\",\"Export\":\"N\",\"CountryName\":\"Nagpur\",\"SourceCity\":\"440001\"},{\"ProductID\":\"1234\",\"ProductName\":\"Chairs\",\"Price\":\"1000\",\"Export\":\"Y\",\"CountryName\":\"AUS\",\"SourceCity\":\"Mumbai\",\"CountryCode\":\"61\",\"SourcePincode\\r\":\"400001\"},{\"ProductID\":\"\"}]"
        val columnValidator = ColumnValidation()
        val expected = "[]"

        val actual = columnValidator.getInvalidFieldNames(configJSON, jsonData).toString()

        assertEquals(expected, actual)

    }

    @Test
    fun shouldReturnOneColumnNameIfOneConfigColumnIsNotInCsvData() {
        val configJSON =
            "[{\"fieldName\":\"ProductIDNumber\",\"type\":\"Number\",\"length\":\"\",\"minLength\":\"\",\"maxLength\":\"\"},{\"fieldName\":\"ProductName\",\"type\":\"Alphabet\",\"length\":\"\",\"minLength\":\"\",\"maxLength\":\"\"}]"
        val jsonData =
            "[{\"ProductID\":\"1564\",\"ProductName\":\"Table\",\"Price\":\"4500.59\",\"Export\":\"N\",\"CountryName\":\"Nagpur\",\"SourceCity\":\"440001\"},{\"ProductID\":\"1234\",\"ProductName\":\"Chairs\",\"Price\":\"1000\",\"Export\":\"Y\",\"CountryName\":\"AUS\",\"SourceCity\":\"Mumbai\",\"CountryCode\":\"61\",\"SourcePincode\\r\":\"400001\"},{\"ProductID\":\"\"}]"
        val columnValidator = ColumnValidation()
        val expected = "[{\"Column Name Error\":\"ProductIDNumber\"}]"

        val actual = columnValidator.getInvalidFieldNames(configJSON, jsonData).toString()

        assertEquals(expected, actual)

    }

    @Test
    fun shouldReturnTwoColumnNameIfTwoConfigColumnIsNotInCsvData() {
        val configJSON =
            "[{\"fieldName\":\"ProductIDNumber\",\"type\":\"Number\",\"length\":\"\",\"minLength\":\"\",\"maxLength\":\"\"},{\"fieldName\":\"ProductNamed\",\"type\":\"Alphabet\",\"length\":\"\",\"minLength\":\"\",\"maxLength\":\"\"},{\"fieldName\":\"CountryName\",\"type\":\"Alphabet\",\"length\":\"\",\"minLength\":\"\",\"maxLength\":\"\"}]"
        val jsonData =
            "[{\"ProductID\":\"1564\",\"ProductName\":\"Table\",\"Price\":\"4500.59\",\"Export\":\"N\",\"CountryName\":\"Nagpur\",\"SourceCity\":\"440001\"},{\"ProductID\":\"1234\",\"ProductName\":\"Chairs\",\"Price\":\"1000\",\"Export\":\"Y\",\"CountryName\":\"AUS\",\"SourceCity\":\"Mumbai\",\"CountryCode\":\"61\",\"SourcePincode\\r\":\"400001\"},{\"ProductID\":\"\"}]"
        val columnValidator = ColumnValidation()
        val expected = "[{\"Column Name Error\":\"ProductIDNumber\"},{\"Column Name Error\":\"ProductNamed\"}]"

        val actual = columnValidator.getInvalidFieldNames(configJSON, jsonData).toString()

        assertEquals(expected, actual)

    }
}