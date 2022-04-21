import routeHandler.RouteHandler
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket

class ClientHandler {

    fun handleClient(clientSocket: Socket, routeHandler: RouteHandler) {
        val outputStream = BufferedWriter(OutputStreamWriter(clientSocket.getOutputStream()))
        val inputStream = BufferedReader(InputStreamReader(clientSocket.getInputStream()))

        val request = readRequest(inputStream)
        println("request $request")
        val responseData = routeHandler.handleRequest(request, inputStream)

        sendResponseToClient(outputStream, responseData)

        clientSocket.close()
    }

    private fun sendResponseToClient(outputStream: BufferedWriter, responseData: String) {
        outputStream.write(responseData)
        outputStream.flush()
    }

    private fun readRequest(inputStream: BufferedReader): String {
        var request = ""
        var flag = true
        while (flag) {
            val line = inputStream.readLine()
            request += line + "\r\n"
            if (line == null || line.isEmpty()) {
                flag = false
            }
        }
        return request
    }
}