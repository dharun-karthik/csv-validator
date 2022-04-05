import routeHandler.Get
import routeHandler.Post
import routeHandler.Unknown
import java.io.*
import java.net.ServerSocket


class Server(
    port: Int = 3000
) {
    private val serverSocket = ServerSocket(port)
    private val get = Get()
    private val post = Post()
    private val unknown = Unknown()

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
        val responseData = handleRequest(request, inputStream)

        getResponseData(outputStream, responseData)

        clientSocket.close()
    }

    private fun getResponseData(outputStream: BufferedWriter, responseData: String) {
        outputStream.write(responseData)
        outputStream.flush()
    }

    private fun handleRequest(request: String, inputStream: BufferedReader): String {
        return when (getRequestType(request)) {
            "GET" -> get.handleGetRequest(request)
            "POST" -> post.handlePostRequest(request, inputStream)
            else -> unknown.handleUnknownRequest()
        }
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

    private fun getRequestType(request: String): String {
        return request.substringBefore(" ")
    }
}
