import routeHandler.RequestHandler
import java.io.*
import java.net.ServerSocket


class Server(
    port: Int = 3000
){
    private val requestHandler = RequestHandler()
    private val serverSocket = ServerSocket(port)

    fun startServer() {
        while (true) {
            handleClient()
        }
    }

    private fun handleClient() {
        val clientSocket = serverSocket.accept()
        val outputStream = BufferedWriter(OutputStreamWriter(clientSocket.getOutputStream()))
        val inputStream = BufferedReader(InputStreamReader(clientSocket.getInputStream()))

        val request = readRequest(inputStream)
        println("request $request")
        val responseData = requestHandler.handleRequest(request, inputStream)

        getResponseData(outputStream, responseData)

        clientSocket.close()
    }

    private fun getResponseData(outputStream: BufferedWriter, responseData: String) {
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
