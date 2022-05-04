package db

import com.google.gson.Gson
import metaData.template.DependencyTemplate
import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ConfigReaderWriterTest {
    internal class ReadConfigTest {

        @Test
        fun shouldReadTheConfigWithTypeOnly() {

        }

        @Test
        fun shouldReadTheConfigWithLength() {

        }

        @Test
        fun shouldReadTheConfigWithDependency() {

        }

        @Test
        fun shouldReadTheConfigWithDateTimePattern() {

        }
    }

    internal class WriteConfigTest {

        @Test
        fun shouldWriteTheDataWithFieldNameAndTypeOnly() {
            val configName = "nameAndType"
            val jsonData = createArrayOfJsonTemplate()
            val expected = ""

            ConfigReaderWriter().addConfig(configName, jsonData)
            val actual = "executeQuery()"

            assertEquals(expected, actual)
        }

        @Test
        fun shouldWriteTheDataWithLengthParameters() {
            val configName = "nameAndType"
            val jsonData = createArrayOfJsonTemplate( length = true)
            val expected = ""

            ConfigReaderWriter().addConfig(configName, jsonData)
            val actual = "executeQuery()"

            assertEquals(expected, actual)
        }

        @Test
        fun shouldWriteTheDataWithDependencies() {
            val configName = "nameAndType"
            val jsonData = createArrayOfJsonTemplate(dependency = true)
            val expected = ""

            ConfigReaderWriter().addConfig(configName, jsonData)
            val actual = "executeQuery()"

            assertEquals(expected, actual)
        }

        @Test
        fun shouldWriteTheDataWithDateTimePattern() {
            val configName = "nameAndType"
            val jsonData = createArrayOfJsonTemplate(dependency = true)
            val expected = ""

            ConfigReaderWriter().addConfig(configName, jsonData)
            val actual = "executeQuery()"

            assertEquals(expected, actual)
        }

        private fun createArrayOfJsonTemplate(
            length: Boolean = false,
            dependency: Boolean = false,
            dateTime: Boolean = false
        ): Array<JsonConfigTemplate> {

            val jsonString = """[{"fieldName":"Product Id","type":"text"},{"fieldName":"Product Description","type":"email"},{"fieldName":"Price","type":"alphabets"},{"fieldName":"Export","type":"number"},{"fieldName":"Country Name","type":"text"},{"fieldName":"Source City","type":"text"},{"fieldName":"Country Code","type":"text"},{"fieldName":"Source Pincode","type":"text"}]"""
            val jsonConfigTemplateObjectList = createBasicJsonTemplateObjects(jsonString)

            if (length) {
                addLengthToAllFields(jsonConfigTemplateObjectList)
            }
            if (dependency) {
                addDependencyToAllFields(jsonConfigTemplateObjectList)
            }
            if (dateTime) {
                changeTypeToTimeAndAddPattern(jsonConfigTemplateObjectList)
            }
            return jsonConfigTemplateObjectList
        }

        private fun changeTypeToTimeAndAddPattern(jsonConfigTemplateObject: Array<JsonConfigTemplate>) {
            jsonConfigTemplateObject.forEach {
                it.type = "Datetime"
                it.pattern = "hh:mm:ss"
            }
        }

        private fun addDependencyToAllFields(jsonConfigTemplateObject: Array<JsonConfigTemplate>) {
            jsonConfigTemplateObject.forEach {
                it.dependencies = listOf(DependencyTemplate("colOne", "yes", "!null"))
            }
        }

        private fun addLengthToAllFields(jsonConfigTemplateObject: Array<JsonConfigTemplate>) {
            jsonConfigTemplateObject.forEach {
                it.length = 8
            }
        }

        private fun createBasicJsonTemplateObjects(jsonString: String): Array<JsonConfigTemplate> {
            return Gson().fromJson(jsonString, Array<JsonConfigTemplate>::class.java)
        }

    }
}