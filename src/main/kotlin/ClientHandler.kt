import routeHandler.InputStreamProvider
import routeHandler.RouteHandler
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.Socket

class ClientHandler {

    fun handleClient(clientSocket: Socket, routeHandler: RouteHandler) {
        val outputStream = BufferedWriter(OutputStreamWriter(clientSocket.getOutputStream()))
        val inputStreamProvider = InputStreamProvider(clientSocket.getInputStream())

        val request = readRequest(inputStreamProvider)
        println(request)
        val responseData = routeHandler.handleRequest(request, inputStreamProvider)

        sendResponseToClient(outputStream, responseData)

        clientSocket.close()
    }

    private fun sendResponseToClient(outputStream: BufferedWriter, responseData: String) {
        outputStream.write(responseData)
        outputStream.flush()
    }

    private fun readRequest(inputStreamProvider: InputStreamProvider): String {
        val bufferedInputStream = inputStreamProvider.getBufferedReader()
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