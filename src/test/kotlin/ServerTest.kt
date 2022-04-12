import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.net.Socket

internal class ServerTest {

    @Test
    fun shouldReturnConnectExceptionIfServerNotStarted() {
        val port = 3001
        var actual = true

        try {
            Socket("0.0.0.0", port)
        } catch (e: Exception) {
            actual = false
        }

        assertFalse(actual)
    }

    @Test
    fun shouldConnectToServerIfServerIsStarted() {
        val port = 3001
        var actual = true

        Server(3001)
        try {
            Socket("0.0.0.0", port)
        } catch (e: Exception) {
            actual = false
        }

        assertTrue(actual)
    }

    @Test
    fun shouldReturnConnectExceptionIfServerIsStopped() {
        val port = 3001
        var actual = true
        val server = Server(3001)

        server.stopServer()
        try {
            Socket("0.0.0.0", port)
        } catch (e: Exception) {
            actual = false
        }

        assertFalse(actual)
    }
}