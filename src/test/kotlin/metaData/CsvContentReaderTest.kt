package metaData

import metaData.csv.CsvContentReader
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class CsvContentReaderTest {
    @Test
    fun shouldBeAbleToReadJsonData() {
        val csvContentReader =
            CsvContentReader("src/test/kotlin/metaDataTestFiles/csvContent/read-json-content-test.csv")

        val actual = csvContentReader.readNextLineInJson()
        val expected = """{"hello":"not ,bad","Product Description":"I hope this works"}"""

        assertEquals(expected, actual.toString())
    }

    @Test
    fun shouldBeAbleToReadJsonDataAsNullWhenValueIsEmpty() {
        val csvContentReader =
            CsvContentReader("src/test/kotlin/metaDataTestFiles/csvContent/read-json-content-test.csv")

        for (i in 1..6) {
            csvContentReader.readNextLineInJson()
        }

        val actual = csvContentReader.readNextLineInJson()
        val expected = """{"hello":"null","Product Description":"null"}"""

        assertEquals(expected, actual.toString())
    }

    @Test
    fun shouldGetNullWhenAllContentsFromTheFileAreRead() {
        val csvContentReader =
            CsvContentReader("src/test/kotlin/metaDataTestFiles/csvContent/read-json-content-test.csv")
        for (i in 1..8) {
            csvContentReader.readNextLineInJson()
        }

        val actual = csvContentReader.readNextLineInJson()

        assertNull(actual)
    }
}