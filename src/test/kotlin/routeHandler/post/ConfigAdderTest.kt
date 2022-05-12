package routeHandler.post

import db.DBConfigReaderWriter
import db.DBConnection
import fakeStreams.FakeInputStreamProvider
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.EnvVars

internal class ConfigAdderTest {

    init {
        EnvVars.setTestDbEnvVars()
    }
    @Test
    fun shouldAddConfigToTheDB() {
        val jsonString =
            """{"config_1":[{"fieldName":"Product Id","type":"text"},{"fieldName":"Product Description","type":"email"},{"fieldName":"Price","type":"alphabets"},{"fieldName":"Export","type":"number"},{"fieldName":"Country Name","type":"text"},{"fieldName":"Source City","type":"text"},{"fieldName":"Country Code","type":"text"},{"fieldName":"Source Pincode","type":"text"}]}"""
        val inputStream = FakeInputStreamProvider(jsonString)
        val request = """
            Content-Length: ${jsonString.length}
            
        """
        val expected =
            """JsonConfigTemplate(fieldName=Product Id, type=text, isNullAllowed=null, pattern=null, length=0, minLength=0, maxLength=0, dependencies=null, values=null)"""

        DBConnection.initialise("jdbc:h2:~/db;MODE=postgresql;INIT=RUNSCRIPT FROM 'src/test/kotlin/resources/createAndPopulateH2Db.sql'")
        ConfigAdder().handle(request, inputStream)
        val actual = DBConfigReaderWriter().readConfig("config_1").first().toString()

        assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnStatusCode200AndSuccessIfSuccessFullyAdded() {
        val jsonString =
            """{"config_2":[{"fieldName":"Product Id","type":"text"},{"fieldName":"Product Description","type":"email"},{"fieldName":"Price","type":"alphabets"},{"fieldName":"Export","type":"number"},{"fieldName":"Country Name","type":"text"},{"fieldName":"Source City","type":"text"},{"fieldName":"Country Code","type":"text"},{"fieldName":"Source Pincode","type":"text"}]}"""
        val inputStream = FakeInputStreamProvider(jsonString)
        val request = """
            Content-Length: ${jsonString.length}
            
        """
        val expected = """HTTP/1.1 200 OK
                        |Content-Type: text/html; charset=utf-8
                        |Content-Length: 7

                        |success""".trimMargin()

        DBConnection.initialise("jdbc:h2:~/db;MODE=postgresql;INIT=RUNSCRIPT FROM 'src/test/kotlin/resources/createAndPopulateH2Db.sql'")
        val actual = ConfigAdder().handle(request, inputStream)

        assertEquals(expected, actual)
    }
}