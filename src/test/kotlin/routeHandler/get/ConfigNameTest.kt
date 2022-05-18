package routeHandler.get

import db.DBConnection
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.EnvVars

internal class ConfigNameTest {
    init {
        EnvVars.setTestDbEnvVars()
        DBConnection.initialise("jdbc:h2:~/db;MODE=postgresql;INIT=RUNSCRIPT FROM 'src/test/kotlin/resources/config_names.sql'")
    }

    @Test
    fun shouldGetConfigNames() {
        val configName = ConfigName()
        val expected = """HTTP/1.1 200 OK
                         |Content-Type: application/json; charset=utf-8
                         |Content-Length: 26

                         |["first","second","third"]""".trimMargin()

        val actual = configName.getConfigNames()

        assertEquals(expected, actual)
    }
}