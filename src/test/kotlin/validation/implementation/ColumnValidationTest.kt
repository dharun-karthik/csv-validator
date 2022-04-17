package validation.implementation

import org.json.JSONArray
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ColumnValidationTest {

    @Test
    fun shouldReturnEmptyJsonArrayIfAllConfigColumnsArePresentInCsvData() {
        val configJSON =
            """[{"fieldName":"ProductID","type":"Number","length":"","minLength":"","maxLength":""},{"fieldName":"ProductName","type":"Alphabet","length":"","minLength":"","maxLength":""}]"""
        val jsonData = JSONArray(
            """[{"ProductID":"1564","ProductName":"Table","Price":"4500.59","Export":"N","CountryName":"Nagpur","SourceCity":"440001"},{"ProductID":"1234","ProductName":"Chairs","Price":"1000","Export":"Y","CountryName":"AUS","SourceCity":"Mumbai","CountryCode":"61","SourcePincode\\r":"400001"},{"ProductID":""}]"""
        )
        val columnValidator = ColumnValidation()
        val expected = "[]"

        val actual = columnValidator.getInvalidFieldNames(configJSON, jsonData).toString()

        assertEquals(expected, actual)

    }

    @Test
    fun shouldReturnOneColumnNameIfOneConfigColumnIsNotInCsvData() {
        val configJSON =
            """[{"fieldName":"ProductIDNumber","type":"Number","length":"","minLength":"","maxLength":""},{"fieldName":"ProductName","type":"Alphabet","length":"","minLength":"","maxLength":""}]"""
        val jsonData = JSONArray(
            """[{"ProductID":"1564","ProductName":"Table","Price":"4500.59","Export":"N","CountryName":"Nagpur","SourceCity":"440001"},{"ProductID":"1234","ProductName":"Chairs","Price":"1000","Export":"Y","CountryName":"AUS","SourceCity":"Mumbai","CountryCode":"61","SourcePincode\\r":"400001"},{"ProductID":""}]"""
        )
        val columnValidator = ColumnValidation()
        val expected = """[{"Column Name Error":"ProductIDNumber"}]"""

        val actual = columnValidator.getInvalidFieldNames(configJSON, jsonData).toString()

        assertEquals(expected, actual)

    }

    @Test
    fun shouldReturnTwoColumnNameIfTwoConfigColumnIsNotInCsvData() {
        val configJSON =
            """[{"fieldName":"ProductIDNumber","type":"Number","length":"","minLength":"","maxLength":""},{"fieldName":"ProductNamed","type":"Alphabet","length":"","minLength":"","maxLength":""},{"fieldName":"CountryName","type":"Alphabet","length":"","minLength":"","maxLength":""}]"""
        val jsonData = JSONArray(
            """[{"ProductID":"1564","ProductName":"Table","Price":"4500.59","Export":"N","CountryName":"Nagpur","SourceCity":"440001"},{"ProductID":"1234","ProductName":"Chairs","Price":"1000","Export":"Y","CountryName":"AUS","SourceCity":"Mumbai","CountryCode":"61","SourcePincode\\r":"400001"},{"ProductID":""}]"""
        )
        val columnValidator = ColumnValidation()
        val expected = """[{"Column Name Error":"ProductIDNumber"},{"Column Name Error":"ProductNamed"}]"""

        val actual = columnValidator.getInvalidFieldNames(configJSON, jsonData).toString()

        assertEquals(expected, actual)

    }

    @Test
    fun shouldReturnEmptyJsonArrayIgnoringCaseSensitivity() {
        val configJSON =
            """[{"fieldName":"productid","type":"Number","length":"","minLength":"","maxLength":""},{"fieldName":"productname","type":"Alphabet","length":"","minLength":"","maxLength":""}]"""
        val jsonData = JSONArray(
            """[{"ProductID":"1564","ProductName":"Table","Price":"4500.59","Export":"N","CountryName":"Nagpur","SourceCity":"440001"},{"ProductID":"1234","ProductName":"Chairs","Price":"1000","Export":"Y","CountryName":"AUS","SourceCity":"Mumbai","CountryCode":"61","SourcePincode\\r":"400001"},{"ProductID":""}]"""
        )
        val columnValidator = ColumnValidation()
        val expected = "[]"

        val actual = columnValidator.getInvalidFieldNames(configJSON, jsonData).toString()

        assertEquals(expected, actual)

    }

    internal class GetColumnsNotInConfigTest() {

        @Test
        fun shouldReturnEmptyJsonIfAllColumnsArePresent() {
            val columnValidator = ColumnValidation()
            val configJSON =
                """[{"fieldName":"ProductID","type":"Number","length":"","minLength":"","maxLength":""},{"fieldName":"ProductName","type":"Alphabet","length":"","minLength":"","maxLength":""}]"""
            val jsonData = JSONArray(
                """[{"ProductID":"1564","ProductName":"Table"},{"ProductID":"1234","ProductName":"Chairs"}]"""
            )
            val expected = "[]"

            val actual = columnValidator.getColumnsNotInConfig(configJSON, jsonData).toString()

            assertEquals(expected, actual)
        }

        @Test
        fun shouldReturnAllTheColumnsNotInTest() {
            val columnValidator = ColumnValidation()
            val configJSON =
                """[{"fieldName":"ProductID","type":"Number","length":"","minLength":"","maxLength":""},{"fieldName":"ProductName","type":"Alphabet","length":"","minLength":"","maxLength":""}]"""
            val jsonData = JSONArray(
                """[{"ProductID":"1564","ProductName":"Table","Price":"4500.59","Export":"N","CountryName":"Nagpur","SourceCity":"440001"},{"ProductID":"1234","ProductName":"Chairs","Price":"1000","Export":"Y","CountryName":"AUS","SourceCity":"Mumbai","CountryCode":"61","SourcePincode\\r":"400001"},{"ProductID":""}]"""
            )
            val expected =
                """[{"Column unavailable in config":"price"},{"Column unavailable in config":"countryname"},{"Column unavailable in config":"export"},{"Column unavailable in config":"sourcecity"}]"""

            val actual = columnValidator.getColumnsNotInConfig(configJSON, jsonData).toString()

            assertEquals(expected, actual)
        }

        @Test
        fun shouldReturnAllTheColumnsNotInTestIgnoringCaseSensitivity() {
            val columnValidator = ColumnValidation()
            val configJSON =
                """[{"fieldName":"productid","type":"Number","length":"","minLength":"","maxLength":""},{"fieldName":"ProductName","type":"Alphabet","length":"","minLength":"","maxLength":""}]"""
            val jsonData = JSONArray(
                """[{"ProductID":"1564","productname":"Table","Price":"4500.59","Export":"N","CountryName":"Nagpur","SourceCity":"440001"},{"ProductID":"1234","ProductName":"Chairs","Price":"1000","Export":"Y","CountryName":"AUS","SourceCity":"Mumbai","CountryCode":"61","SourcePincode\\r":"400001"},{"ProductID":""}]"""
            )
            val expected =
                """[{"Column unavailable in config":"price"},{"Column unavailable in config":"countryname"},{"Column unavailable in config":"export"},{"Column unavailable in config":"sourcecity"}]"""

            val actual = columnValidator.getColumnsNotInConfig(configJSON, jsonData).toString()

            assertEquals(expected, actual)
        }

    }
}