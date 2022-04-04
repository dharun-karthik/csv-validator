import com.google.gson.Gson
import org.json.JSONArray
import routeHandler.Get
import routeHandler.Post
import routeHandler.Unknown
import validation.DuplicationValidation
import java.io.*
import java.net.ServerSocket


class Server(
    port: Int = 3000
) {
    private lateinit var fieldArray: Array<JsonMetaDataTemplate>
    private val serverSocket = ServerSocket(port)
    private val statusMap = mapOf(
        200 to "Found",
        400 to "Bad Request",
        401 to "Unauthorized"
    )
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
            "POST" -> handlePostRequest(request, inputStream)
            else -> unknown.handleUnknownRequest()
        }
    }

    private fun handlePostRequest(request: String, inputStream: BufferedReader): String {

        return when (getPath(request)) {
            "/csv" -> handleCsv(request, inputStream)
            "/add-meta-data" -> handleAddingCsvMetaData(request, inputStream)
            else -> unknown.handleUnknownRequest()
        }
    }


    private fun getPath(request: String): String {
        return request.split("\r\n")[0].split(" ")[1].substringBefore("?")
    }

    private fun handleCsv(request: String, inputStream: BufferedReader): String {
        val bodySize = getContentLength(request)
        val body = getBody(bodySize, inputStream)
        println("body $body")
        val jsonBody = JSONArray(body)

        val repeatedRowList = DuplicationValidation().getDuplicateRowNumberInJSON(jsonBody)
        println("Repeated Lines :$repeatedRowList")
        lateinit var responseBody: String
        responseBody += "{"
        responseBody = if (repeatedRowList.isNotEmpty()) {
            "\"Repeated Lines\" : \"$repeatedRowList\""
        } else {
            "No Error"
        }
        responseBody += "}"
        val contentLength = responseBody.length
        val endOfHeader = "\r\n\r\n"
        return getHttpHead(200) + """Content-Type: text/json; charset=utf-8
            |Content-Length: $contentLength""".trimMargin() + endOfHeader + responseBody
    }

    private fun handleAddingCsvMetaData(request: String, inputStream: BufferedReader): String {
        println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&")
        val bodySize = getContentLength(request)
        val body = getBody(bodySize, inputStream)
        println(body)
        val jsonBody = getMetaData(body)
        println(body)
        fieldArray = jsonBody
        val endOfHeader = "\r\n\r\n"
        val responseBody = "Successfully Added"
        val contentLength = responseBody.length
        return getHttpHead(200) + """Content-Type: text/plain; charset=utf-8
            |Content-Length: $contentLength""".trimMargin() + endOfHeader + responseBody
    }

    private fun getBody(bodySize: Int, inputStream: BufferedReader): String {
        val buffer = CharArray(bodySize)
        inputStream.read(buffer)
        return String(buffer)
    }

    fun getMetaData(body: String): Array<JsonMetaDataTemplate> {
        val gson = Gson()
        return gson.fromJson(body, Array<JsonMetaDataTemplate>::class.java)
    }

    private fun getRequestType(request: String): String {
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

    private fun getHttpHead(statusCode: Int): String {
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
