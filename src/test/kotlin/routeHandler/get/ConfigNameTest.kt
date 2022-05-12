package routeHandler.get

import db.DBConnection
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.EnvVars

internal class ConfigNameTest {
    init {
        EnvVars.setTestDbEnvVars()
        DBConnection.initialise("~/db;MODE=postgresql;INIT=RUNSCRIPT FROM 'src/test/kotlin/resources/config_names.sql'")
    }

    @Test
    fun shouldGetConfigNames() {
        val configName = ConfigName()

        val actual = configName.getConfigNames()
        val expected = """HTTP/1.1 200 OK
                         |Content-Type: application/json; charset=utf-8
                         |Content-Length: 26

                         |["first","second","third"]""".trimMargin()

        assertEquals(expected, actual)
    }
}