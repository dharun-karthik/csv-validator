package routeHandler

import request.RequestHandle
import response.ContentType
import response.Response
import java.io.File

class GetRouteHandler() {
    private val response: Response = Response()
    private val requestHandle = RequestHandle()


    fun handleGetRequest(request: String): String {
        return when (val path = requestHandle.getPath(request)) {
            "/" -> serveFile("/index.html")
            "/get-meta-data" -> serveMetaDataJson()
            else -> serveFile(path)
        }
    }

    private fun serveMetaDataJson(): String {
        val responseBody = readFileContent("/csv-meta-data.json")
        return response.generateResponse(responseBody,200, ContentType.JSON)
    }

    private fun serveFile(path: String): String {
        val responseBody = readFileContent(path)
        return response.generateResponse(responseBody, 200, ContentType.HTML)
    }

    private fun readFileContent(fileName: String): String {
        val path = System.getProperty("user.dir")
        var file = File("$path/src/main/public$fileName")
        if (!file.exists()) {
            file = File("$path/src/main/public/404.html")
        }
        return file.readText(Charsets.UTF_8)
    }
}