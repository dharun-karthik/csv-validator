package metaData

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FileReaderWriterTest {
    @Test
    fun shouldBeAbleToReadRawContent() {
        val fileReaderWriter = FileReaderWriter("src/test/kotlin/metaDataTestFiles/raw/read-raw-content-test.json")
        val expected = """[
  {
    "fieldName": "product id",
    "type": "alphanumeric",
    "length": 5
  }
]"""

        val actual = fileReaderWriter.readRawContent()

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun shouldBeAbleToWriteRawContent() {
        val fileReaderWriter = FileReaderWriter("src/test/kotlin/metaDataTestFiles/raw/write-raw-content-test.json")
        val data = """[{"hello":"123"}]"""

        fileReaderWriter.writeRawContent(data)
        val actual = fileReaderWriter.readRawContent()

        Assertions.assertEquals(data, actual)
    }
}