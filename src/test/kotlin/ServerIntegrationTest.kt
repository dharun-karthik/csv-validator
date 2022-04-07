import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.net.Socket

class ServerIntegrationTest {

    private lateinit var server: Server
    @Test
    fun shouldReturnSuccessWhenLoadingHomePage() {
        val port = 3012
        startServerInThread(port)
        val clientSocket = Socket("localhost", port)
        val outputStream = clientSocket.getOutputStream()
        val inputStream = clientSocket.getInputStream()
        val request = """GET / HTTP/1.1 
                |Host: localhost:3000""".trimMargin() + "\r\n\r\n"
        outputStream.write(request.toByteArray())
        val expectedResponseCode = "200"
        val response = String(inputStream.readAllBytes())

        val actualResponseCode = extractResponseCode(response)
        clientSocket.close()

        assertEquals(expectedResponseCode, actualResponseCode)
    }

    //TODO refactor this test
    @Test
    fun shouldAddMetaDataToFieldArray() {
        val port = 3013
        startServerInThread(port)
        val clientSocket = Socket("localhost", port)
        val outputStream = clientSocket.getOutputStream()
        val inputStream = clientSocket.getInputStream()
        val payload = "[{\"fieldName\": \"ProductId\",\"type\": \"AlphaNumeric\",\"length\": 5}]"
        val request = """POST /add-meta-data HTTP/1.1
                |Accept: */*
                |Content-Length: ${payload.length}
                |Content-Type: text/plain;charset=UTF-8
                |Host: localhost:3000
                |Origin: http://localhost:3000""".trimMargin() + "\r\n\r\n" +
                payload +"\r\n"
        outputStream.write(request.toByteArray())
        val expectedResponseCode = "200"
        val response = String(inputStream.readAllBytes())
        val postRouteHandler= server.postRouteHandler
        val fieldArray= postRouteHandler.fieldArray
        val expectedFieldName="ProductId"
        val expectedType="AlphaNumeric"

        val actualResponseCode = extractResponseCode(response)
        clientSocket.close()

        assertEquals(expectedResponseCode, actualResponseCode)
        assertEquals(expectedFieldName,fieldArray[0].fieldName)
        assertEquals(expectedType,fieldArray[0].type)
    }

    private fun extractResponseCode(response: String): String {
        return response.split(" ")[1]
    }

    private fun startServerInThread(port: Int) {
        Thread {
            server = Server(port)
            server.startServer()
        }.start()
    }
}