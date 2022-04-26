package metaData

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CsvContentReaderTest {
    @Test
    fun shouldBeAbleToReadJsonData() {
        val csvContentReader =
            CsvContentReader("src/test/kotlin/metaDataTestFiles/csvContent/read-json-content-test.csv")

        val actual = csvContentReader.readNextLineInJson()
        val expected = """[{"hello":"hi","Product Description":"Table chair"}]"""

        assertEquals(expected, actual.toString())
    }
}