import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.ServerSocket


class Server(
    port: Int = 3000
) {
    private val serverSocket = ServerSocket(port)
    private val statusMap = mapOf(
        200 to "Found",
        400 to "Bad Request",
        401 to "Unauthorized"
    )

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
        val responseData = handleRequest(request, inputStream)

        outputStream.write(responseData)
        outputStream.flush()

        clientSocket.close()
    }

    fun handleRequest(request: String, inputStream: BufferedReader): String {
        return when (getRequestType(request)) {
            "GET" -> handleGetRequest(request)
            "POST" -> handlePostRequest(request, inputStream)
            else -> handleUnknownRequest()
        }
    }

    private fun handleUnknownRequest(): String {
        return getHttpHead(400) + "\r\n\r\n"
    }

    private fun handlePostRequest(request: String, inputStream: BufferedReader): String {

        return when (getPath(request)) {
            "csv" -> handleCsv(request, inputStream)
            "add-meta-data" -> handleAddingCsvMetaData(request, inputStream)
            else -> handleUnknownRequest()
        }
    }

    private fun handleAddingCsvMetaData(request: String, inputStream: BufferedReader): String {
        TODO("Not yet implemented")
    }

    private fun handleCsv(request: String, inputStream: BufferedReader): String {
        val bodySize = getContentLength(request)
        val body = getBody(bodySize, inputStream)
        val jsonBody = getJsonBody(body)
        val endOfHeader = "\r\n\r\n"
        val responseBody = "Successfully Added"
        val contentLength = responseBody.length
        return getHttpHead(201) + """Content-Type: text/plain; charset=utf-8
            |Content-Length: $contentLength""".trimMargin() + endOfHeader + responseBody
    }

    private fun getBody(bodySize: Int, inputStream: BufferedReader): String {
        val buffer = CharArray(bodySize)
        inputStream.read(buffer)
        return String(buffer)
    }

    fun getJsonBody(body: String): String {
        TODO("get body in json")
    }

    fun getRequestType(request: String): String {
        return request.substringBefore(" ")
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

    private fun handleGetRequest(request: String): String {

        return when (getPath(request)) {
            "/" -> handleHomePage()
            else -> handleUnknownRequest()
        }
    }

    private fun handleHomePage(): String {
        TODO("handle home page")
    }

    fun getPath(request: String): String {
        return request.split("\r\n")[0].split(" ")[1].substringBefore("?").split("/")[1]
    }

    fun getHttpHead(statusCode: Int): String {
        val content = statusMap[statusCode]
        return "HTTP/1.1 $statusCode $content\n"
    }

    private fun getContentLength(request: String): Int {
        request.split("\n").forEach { headerString ->
            val keyValue = headerString.split(":", limit = 2)
            if (keyValue[0].contains("Content-Length")) {
                return keyValue[1].trim().toInt()
            }
        }
        return 0
    }
}