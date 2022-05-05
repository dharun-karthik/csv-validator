package routeHandler.get

import db.DBConnection
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ConfigNameTest {
    init {
        DBConnection.initialise("jdbc:h2:~/db;MODE=postgresql;INIT=RUNSCRIPT FROM 'src/test/kotlin/resources/config_names.sql'")
    }

    @Test
    fun shouldGetConfigNames() {
        val configName = ConfigName()

        val actual = configName.getConfigNames()
        val expected = """["first","second","third"]"""

        assertEquals(expected, actual)
    }
}