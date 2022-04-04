import org.junit.jupiter.api.BeforeAll

class ServerIntegrationTest {
    companion object {
        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            Thread {
                Server().startServer()
            }.start()
        }
    }


}