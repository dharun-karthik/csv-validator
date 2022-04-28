package validation.implementation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class ColumnValidationTest {

    internal class GetColumnsNotInConfigTest {

        @Test
        fun shouldReturnEmptyJsonIfAllColumnsArePresent() {
            val columnValidator = ColumnValidation()
            val configJSON =
                """[{"fieldName":"ProductID","type":"Number","length":"","minLength":"","maxLength":""},{"fieldName":"ProductName","type":"Alphabet","length":"","minLength":"","maxLength":""}]"""
            val csvHeaders = listOf("ProductID", "ProductName")
            val expected = "[]"

            val actual = columnValidator.validate(configJSON, csvHeaders).toString()

            assertEquals(expected, actual)
        }

        @Test
        fun shouldReturnAllTheColumnsNotInConfig() {
            val columnValidator = ColumnValidation()
            val configJSON =
                """[{"fieldName":"ProductID","type":"Number","length":"","minLength":"","maxLength":""},{"fieldName":"ProductName","type":"Alphabet","length":"","minLength":"","maxLength":""}]"""
            val csvHeaders = listOf("ProductID", "ProductName", "Price", "Export", "CountryName", "SourceCity")
            val expected =
                """[{"0":["Price","Export","CountryName","SourceCity"]}]"""

            val actual = columnValidator.validate(configJSON, csvHeaders).toString()

            assertEquals(expected, actual)
        }

        @Test
        fun shouldReturnAllTheColumnsNotInTestIgnoringCaseSensitivity() {
            val columnValidator = ColumnValidation()
            val configJSON =
                """[{"fieldName":"productid","type":"Number","length":"","minLength":"","maxLength":""},{"fieldName":"ProductName","type":"Alphabet","length":"","minLength":"","maxLength":""}]"""
            val csvHeaders = listOf("ProductID", "productname", "Price", "Export", "CountryName", "SourceCity")

            val expected =
                """[{"0":["Price","Export","CountryName","SourceCity"]}]"""

            val actual = columnValidator.validate(configJSON, csvHeaders).toString()

            assertEquals(expected, actual)
        }

    }
}
