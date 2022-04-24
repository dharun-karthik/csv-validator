package validation.implementation

import org.json.JSONArray
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ColumnValidationTest {

    internal class GetColumnsNotInConfigTest {

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
                """[{"0":["price","countryname","export","sourcecity"]}]"""

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
                """[{"0":["price","countryname","export","sourcecity"]}]"""

            val actual = columnValidator.getColumnsNotInConfig(configJSON, jsonData).toString()

            assertEquals(expected, actual)
        }

    }
}