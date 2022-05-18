package metaData

import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class ConfigFileReaderWriterTest {
    @Test
    fun shouldBeAbleToGiveConfigInJson() {
        val configFileReaderWriter =
            ConfigFileReaderWriter("src/test/kotlin/metaDataTestFiles/configContent/csv-config-content-test.json")
        val fields = configFileReaderWriter.readFields()
        val expected = "500020"

        val actual = fields[7].values?.get(0)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldBeAbleToGiveConfigInJsonWhenThereIsNoContent() {
        val configFileReaderWriter =
            ConfigFileReaderWriter("src/test/kotlin/metaDataTestFiles/configContent/empty-json-test.json")

        val expected = configFileReaderWriter.readFields()

        assertNotNull(expected)
    }

    @Test
    fun shouldBeAbleToWriteJsonToFile() {
        val configFileReaderWriter =
            ConfigFileReaderWriter("src/test/kotlin/metaDataTestFiles/configContent/write-csv-config-content-test.json")
        val jsonData: Array<JsonConfigTemplate> =
            arrayOf(
                JsonConfigTemplate(
                    fieldName = "test field",
                    type = "Alphabet",
                    length = 2,
                    minLength = 1,
                    maxLength = 3,
                    values = listOf("22")
                )
            )
        configFileReaderWriter.writeConfigContent(jsonData)
        val expected =
            """[{"fieldName":"test field","type":"Alphabet","length":2,"minLength":1,"maxLength":3,"values":["22"]}]"""

        val actual = configFileReaderWriter.readRawContent()

        assertEquals(expected, actual)
    }
}