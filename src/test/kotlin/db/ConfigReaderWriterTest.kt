package db

import com.google.gson.Gson
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
            val jsonData = createArrayOfJsonTemplate(dateTime = true)
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

            if (length) {
                generateArrayOfConfigWithLengthParameters()
            }
            if (dependency) {
                generateArrayOfConfigWithDependencyParameters()
            }
            if (dateTime) {
                generateArrayOfConfigWithDateAndTimeParameters()
            }
            return generateArrayOfConfigWithoutAnyExtraParameters()
        }

        private fun generateArrayOfConfigWithDateAndTimeParameters(): Array<JsonConfigTemplate> {
            var jsonString = """[{"fieldName":"Product Id","type":"date-time","pattern":"hh:ss:mm a,uuuu-MM-dd"},{"fieldName":"Product Description","type":"time","pattern":"hh:ss:mm a"},{"fieldName":"Price","type":"date","pattern":"dd-MM-uuuu"}]"""
            return Gson().fromJson(jsonString, Array<JsonConfigTemplate>::class.java)
        }

        private fun generateArrayOfConfigWithDependencyParameters(): Array<JsonConfigTemplate> {
            var jsonString = """[{"fieldName":"Product Id","type":"text","dependencies":[{"dependentOn":"Price","expectedDependentFieldValue":"null","expectedCurrentFieldValue":"null"}]},{"fieldName":"Product Description","type":"email","dependencies":[{"dependentOn":"Export","expectedDependentFieldValue":"!null","expectedCurrentFieldValue":"!null"}]},{"fieldName":"Price","type":"alphabets","dependencies":[{"dependentOn":"Export","expectedDependentFieldValue":"!null","expectedCurrentFieldValue":"null"}]}]"""
            return Gson().fromJson(jsonString, Array<JsonConfigTemplate>::class.java)
        }

        private fun generateArrayOfConfigWithLengthParameters(): Array<JsonConfigTemplate> {
            var jsonString = """[{"fieldName":"Product Id","type":"text","minLength":"2","maxLength":"5"},{"fieldName":"Product Description","type":"email","length":"7"},{"fieldName":"Price","type":"alphabets","minLength":"8","maxLength":"12"},{"fieldName":"Export","type":"number","length":"115"},{"fieldName":"Country Name","type":"text","maxLength":"5"},{"fieldName":"Source City","type":"text","minLength":"3"},{"fieldName":"Country Code","type":"text","maxLength":"3"},{"fieldName":"Source Pincode","type":"text","length":"6"}]"""
            return Gson().fromJson(jsonString, Array<JsonConfigTemplate>::class.java)
        }

        private fun generateArrayOfConfigWithoutAnyExtraParameters(): Array<JsonConfigTemplate> {
            val jsonString = """[{"fieldName":"Product Id","type":"text"},{"fieldName":"Product Description","type":"email"},{"fieldName":"Price","type":"alphabets"},{"fieldName":"Export","type":"number"},{"fieldName":"Country Name","type":"text"},{"fieldName":"Source City","type":"text"},{"fieldName":"Country Code","type":"text"},{"fieldName":"Source Pincode","type":"text"}]"""
            return Gson().fromJson(jsonString, Array<JsonConfigTemplate>::class.java)
        }

    }
}