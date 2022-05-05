package db

import com.google.gson.Gson
import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DBConfigReaderWriterTest {
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
            DBConnection.initialise("jdbc:h2:~/db;MODE=postgresql;INIT=RUNSCRIPT FROM 'src/test/kotlin/resources/createAndPopulateH2Db.sql'")
            val expected = "[JsonConfigTemplate(fieldName=Product Id, type=text, isNullAllowed=null, pattern=null, length=0, minLength=0, maxLength=0, dependencies=null, values=null), JsonConfigTemplate(fieldName=Product Description, type=email, isNullAllowed=null, pattern=null, length=0, minLength=0, maxLength=0, dependencies=null, values=null), JsonConfigTemplate(fieldName=Price, type=alphabets, isNullAllowed=null, pattern=null, length=0, minLength=0, maxLength=0, dependencies=null, values=null), JsonConfigTemplate(fieldName=Export, type=number, isNullAllowed=null, pattern=null, length=0, minLength=0, maxLength=0, dependencies=null, values=null), JsonConfigTemplate(fieldName=Country Name, type=text, isNullAllowed=null, pattern=null, length=0, minLength=0, maxLength=0, dependencies=null, values=null), JsonConfigTemplate(fieldName=Source City, type=text, isNullAllowed=null, pattern=null, length=0, minLength=0, maxLength=0, dependencies=null, values=null), JsonConfigTemplate(fieldName=Country Code, type=text, isNullAllowed=null, pattern=null, length=0, minLength=0, maxLength=0, dependencies=null, values=null), JsonConfigTemplate(fieldName=Source Pincode, type=text, isNullAllowed=null, pattern=null, length=0, minLength=0, maxLength=0, dependencies=null, values=null)]"

            DBConfigReaderWriter().writeConfig(configName, jsonData)
            val actual = DBConfigReaderWriter().readConfig(configName).toString()

            assertEquals(expected, actual)
        }

        @Test
        fun shouldWriteTheDataWithLengthParameters() {
            val configName = "nameAndTypeWithLength"
            val jsonData = createArrayOfJsonTemplate( length = true)
            val expected = "[JsonConfigTemplate(fieldName=Product Id, type=text, isNullAllowed=null, pattern=null, length=0, minLength=2, maxLength=5, dependencies=null, values=null), JsonConfigTemplate(fieldName=Product Description, type=email, isNullAllowed=null, pattern=null, length=7, minLength=0, maxLength=0, dependencies=null, values=null), JsonConfigTemplate(fieldName=Price, type=alphabets, isNullAllowed=null, pattern=null, length=0, minLength=8, maxLength=12, dependencies=null, values=null), JsonConfigTemplate(fieldName=Export, type=number, isNullAllowed=null, pattern=null, length=115, minLength=0, maxLength=0, dependencies=null, values=null), JsonConfigTemplate(fieldName=Country Name, type=text, isNullAllowed=null, pattern=null, length=0, minLength=0, maxLength=5, dependencies=null, values=null), JsonConfigTemplate(fieldName=Source City, type=text, isNullAllowed=null, pattern=null, length=0, minLength=3, maxLength=0, dependencies=null, values=null), JsonConfigTemplate(fieldName=Country Code, type=text, isNullAllowed=null, pattern=null, length=0, minLength=0, maxLength=3, dependencies=null, values=null), JsonConfigTemplate(fieldName=Source Pincode, type=text, isNullAllowed=null, pattern=null, length=6, minLength=0, maxLength=0, dependencies=null, values=null)]"
            DBConnection.initialise("jdbc:h2:~/db;MODE=postgresql;INIT=RUNSCRIPT FROM 'src/test/kotlin/resources/createAndPopulateH2Db.sql'")

            DBConfigReaderWriter().writeConfig(configName, jsonData)
            val actual = DBConfigReaderWriter().readConfig(configName).toString()

            assertEquals(expected, actual)
        }

        @Test
        fun shouldWriteTheDataWithDependencies() {
            val configName = "nameAndTypeWithDependencies"
            val jsonData = createArrayOfJsonTemplate(dependency = true)
            val expected = "[JsonConfigTemplate(fieldName=Product Id, type=text, isNullAllowed=null, pattern=null, length=0, minLength=0, maxLength=0, dependencies=[DependencyTemplate(dependentOn=Price, expectedDependentFieldValue=null, expectedCurrentFieldValue=null)], values=null), JsonConfigTemplate(fieldName=Product Description, type=email, isNullAllowed=null, pattern=null, length=0, minLength=0, maxLength=0, dependencies=[DependencyTemplate(dependentOn=Export, expectedDependentFieldValue=!null, expectedCurrentFieldValue=!null)], values=null), JsonConfigTemplate(fieldName=Price, type=alphabets, isNullAllowed=null, pattern=null, length=0, minLength=0, maxLength=0, dependencies=[DependencyTemplate(dependentOn=Export, expectedDependentFieldValue=!null, expectedCurrentFieldValue=null)], values=null)]"
            DBConnection.initialise("jdbc:h2:~/db;MODE=postgresql;INIT=RUNSCRIPT FROM 'src/test/kotlin/resources/createAndPopulateH2Db.sql'")

            DBConfigReaderWriter().writeConfig(configName, jsonData)
            val actual = DBConfigReaderWriter().readConfig(configName).toString()

            assertEquals(expected, actual)
        }

        @Test
        fun shouldWriteTheDataWithDateTimePattern() {
            val configName = "nameAndTypeAsDateTime"
            val jsonData = createArrayOfJsonTemplate(dateTime = true)
            val expected = "[JsonConfigTemplate(fieldName=Product Id, type=date-time, isNullAllowed=null, pattern=hh:ss:mm a,uuuu-MM-dd, length=0, minLength=0, maxLength=0, dependencies=null, values=null), JsonConfigTemplate(fieldName=Product Description, type=time, isNullAllowed=null, pattern=hh:ss:mm a, length=0, minLength=0, maxLength=0, dependencies=null, values=null), JsonConfigTemplate(fieldName=Price, type=date, isNullAllowed=null, pattern=dd-MM-uuuu, length=0, minLength=0, maxLength=0, dependencies=null, values=null)]"
            DBConnection.initialise("jdbc:h2:~/db;MODE=postgresql;INIT=RUNSCRIPT FROM 'src/test/kotlin/resources/createAndPopulateH2Db.sql'")

            DBConfigReaderWriter().writeConfig(configName, jsonData)
            val actual = DBConfigReaderWriter().readConfig(configName).toString()

            assertEquals(expected, actual)
        }

        private fun createArrayOfJsonTemplate(
            length: Boolean = false,
            dependency: Boolean = false,
            dateTime: Boolean = false
        ): Array<JsonConfigTemplate> {

            if (length) {
                return generateArrayOfConfigWithLengthParameters()
            }
            if (dependency) {
                return generateArrayOfConfigWithDependencyParameters()
            }
            if (dateTime) {
                return generateArrayOfConfigWithDateAndTimeParameters()
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