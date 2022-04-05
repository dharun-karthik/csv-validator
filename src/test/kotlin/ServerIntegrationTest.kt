import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.net.Socket

class ServerIntegrationTest {

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

//    @Test
//    fun shouldAddMetaDataToFieldArray() {
//        val port = 3013
//        startServerInThread(port)
//        val clientSocket = Socket("localhost", port)
//        val outputStream = clientSocket.getOutputStream()
//        val inputStream = clientSocket.getInputStream()
//        val request = """POST /add-meta-data HTTP/1.1
//                |Accept: */*
//                |Content-Length: 413
//                |Content-Type: text/plain;charset=UTF-8
//                |Host: localhost:3000
//                |Origin: http://localhost:3000""".trimMargin() + "\r\n\r\n" +
//
//                "[{\"fieldName\": \"ProductId\",\"type\": \"AlphaNumeric\",\"length\": 5}]" +"\r\n"
//
//        println(request)
//        outputStream.write(request.toByteArray())
//        val expectedResponseCode = "200"
//
//        val response = String(inputStream.readAllBytes())
//        val actualResponseCode = extractResponseCode(response)
//        clientSocket.close()
//
//        assertEquals(expectedResponseCode, actualResponseCode)
//    }

    private fun extractResponseCode(response: String): String {
        return response.split(" ")[1]
    }

    private fun startServerInThread(port: Int) {
        Thread {
            val server = Server(port)
            server.startServer()
        }.start()
    }
}