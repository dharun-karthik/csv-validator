package metaData

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class JsonContentReaderWriterTest {
    @Test
    fun shouldBeAbleToReadJsonData() {
        val jsonContentReaderWriter =
            JsonContentReaderWriter("src/test/kotlin/metaDataTestFiles/jsonContent/read-json-content-test.json")

        val actual = jsonContentReaderWriter.readJsonData()
        val expected = """[{"hello":"hi","Product Description":"Table chair"}]"""

        assertEquals(expected, actual.toString())
    }
}