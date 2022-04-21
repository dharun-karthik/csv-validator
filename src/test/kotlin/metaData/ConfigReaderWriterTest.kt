package metaData

import metaData.template.JsonMetaDataTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class ConfigReaderWriterTest {

    @Test
    fun shouldBeAbleToGiveMetaDataInJson() {
        val configReaderWriter = ConfigReaderWriter("src/test/kotlin/metaDataTestFiles/configContent/csv-config-content-test.json")
        val fields = configReaderWriter.readFields()
        val expected = "500020"

        val actual = fields[7].values?.get(0)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldBeAbleToGiveMetaDataInJsonWhenThereIsNoContent() {
        val configReaderWriter = ConfigReaderWriter("src/test/kotlin/metaDataTestFiles/empty-json-test.json")

        val expected = configReaderWriter.readFields()

        assertNotNull(expected)
    }

    @Test
    fun shouldBeAbleToWriteJsonToFile() {
        val configReaderWriter = ConfigReaderWriter("src/test/kotlin/metaDataTestFiles/configContent/write-csv-config-content-test.json")
        val jsonData: Array<JsonMetaDataTemplate> =
            arrayOf(
                JsonMetaDataTemplate(
                    fieldName = "test field",
                    type = "Alphabet",
                    length = "2",
                    minLength = "1",
                    maxLength = "3",
                    values = listOf("22")
                )
            )
        configReaderWriter.writeConfigContent(jsonData)
        val expected =
            """[{"fieldName":"test field","type":"Alphabet","length":"2","minLength":"1","maxLength":"3","values":["22"]}]"""

        val actual = configReaderWriter.readRawContent()

        assertEquals(expected, actual)
    }
}