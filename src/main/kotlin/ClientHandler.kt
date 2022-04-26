import routeHandler.RouteHandler
import java.io.*
import java.net.Socket

class ClientHandler {

    fun handleClient(clientSocket: Socket, routeHandler: RouteHandler) {
        val outputStream = BufferedWriter(OutputStreamWriter(clientSocket.getOutputStream()))
        val inputStream = clientSocket.getInputStream()

        val request = readRequest(inputStream)
        val responseData = routeHandler.handleRequest(request, inputStream)

        sendResponseToClient(outputStream, responseData)

        clientSocket.close()
    }

    private fun sendResponseToClient(outputStream: BufferedWriter, responseData: String) {
        outputStream.write(responseData)
        outputStream.flush()
    }

    private fun readRequest(inputStream: InputStream): String {
        val bufferedInputStream = BufferedReader(InputStreamReader(inputStream))
        var request = ""
        var flag = true
        while (flag) {
            val line = bufferedInputStream.readLine()
            request += line + "\r\n"
            if (line == null || line.isEmpty()) {
                flag = false
            }
        }
        return request
    }
}