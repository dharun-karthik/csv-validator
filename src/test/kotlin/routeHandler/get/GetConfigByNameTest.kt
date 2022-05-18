package routeHandler.get

import db.DBConnection
import fakeStreams.FakeInputStreamProvider
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import routeHandler.post.ConfigAdder

internal class GetConfigByNameTest {

    @Test
    fun shouldGetConfigForRuleNameConfig_1() {
        addDataToH2Database()
        val getConfigByName = GetConfigByName()
        val configuration_name = "config_1"
        val request = """GET /get-config HTTP/1.1
                        |config-name: $configuration_name
                      """.trimMargin()
        val expected = """HTTP/1.1 200 OK
                         |Content-Type: application/json; charset=utf-8
                         |Content-Length: 171

                         |[{"fieldName":"Product Id","minLength":0,"length":0,"type":"text","maxLength":0},{"fieldName":"Product Description","minLength":0,"length":0,"type":"email","maxLength":0}]""".trimMargin()

        val actual = getConfigByName.handle(request)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldNotGetConfigForRuleNameNotExistingInDatabase() {
        val getConfigByName = GetConfigByName()
        val configuration_name = "config_2"
        val request = """GET /get-config HTTP/1.1
                        |config-name: $configuration_name
                      """.trimMargin()
        val expected = """HTTP/1.1 200 OK
                         |Content-Type: application/json; charset=utf-8
                         |Content-Length: 2

                         |[]""".trimMargin()

        val actual = getConfigByName.handle(request)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldGetBadRequestAsResponseForIncompleteHeader() {
        val getConfigByName = GetConfigByName()
        val request = """GET /get-config HTTP/1.1
                      """.trimMargin()
        val expected = """HTTP/1.1 400 Bad Request
                         |Content-Type: text/html; charset=utf-8
                         |Content-Length: 14

                         |No config name""".trimMargin()

        val actual = getConfigByName.handle(request)

        assertEquals(expected, actual)
    }

    private fun addDataToH2Database() {
        val jsonString = """{"config_1":[{"fieldName":"Product Id","type":"text"},{"fieldName":"Product Description","type":"email"}]}"""
        val inputStream = FakeInputStreamProvider(jsonString)
        val request = """
            Content-Length: ${jsonString.length}
            
        """
        DBConnection.initialise("jdbc:h2:~/db;MODE=postgresql;INIT=RUNSCRIPT FROM 'src/test/kotlin/resources/createAndPopulateH2Db.sql'")
        ConfigAdder().handle(request, inputStream)
    }
}
