package validation

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ConfigValidationTest {

    @Test
    fun shouldReturnTrueIfAllConfigColumnsArePresentInCsvData(){
        val configJSON = "[{\"fieldName\":\"ProductID\",\"type\":\"Number\",\"length\":\"\",\"minLength\":\"\",\"maxLength\":\"\"},{\"fieldName\":\"ProductName\",\"type\":\"Alphabet\",\"length\":\"\",\"minLength\":\"\",\"maxLength\":\"\"}]"
        val jsonData = "[{\"ProductID\":\"1564\",\"ProductName\":\"Table\",\"Price\":\"4500.59\",\"Export\":\"N\",\"CountryName\":\"Nagpur\",\"SourceCity\":\"440001\"},{\"ProductID\":\"1234\",\"ProductName\":\"Chairs\",\"Price\":\"1000\",\"Export\":\"Y\",\"CountryName\":\"AUS\",\"SourceCity\":\"Mumbai\",\"CountryCode\":\"61\",\"SourcePincode\\r\":\"400001\"},{\"ProductID\":\"\"}]"
        val columnValidator = ColumnValidation()

        val actual = columnValidator.isValid(configJSON, jsonData)

        assertTrue(actual)

    }

    @Test
    fun shouldReturnFalseIfConfigColumnIsNotInCsvData(){
        val configJSON = "[{\"fieldName\":\"ProductIDNumber\",\"type\":\"Number\",\"length\":\"\",\"minLength\":\"\",\"maxLength\":\"\"},{\"fieldName\":\"ProductName\",\"type\":\"Alphabet\",\"length\":\"\",\"minLength\":\"\",\"maxLength\":\"\"}]"
        val jsonData = "[{\"ProductID\":\"1564\",\"ProductName\":\"Table\",\"Price\":\"4500.59\",\"Export\":\"N\",\"CountryName\":\"Nagpur\",\"SourceCity\":\"440001\"},{\"ProductID\":\"1234\",\"ProductName\":\"Chairs\",\"Price\":\"1000\",\"Export\":\"Y\",\"CountryName\":\"AUS\",\"SourceCity\":\"Mumbai\",\"CountryCode\":\"61\",\"SourcePincode\\r\":\"400001\"},{\"ProductID\":\"\"}]"
        val columnValidator = ColumnValidation()

        val actual = columnValidator.isValid(configJSON, jsonData)

        assertFalse(actual)

    }
}