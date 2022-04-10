package routeHandler

import response.ContentType
import response.Response
import java.io.File

class GetRouteHandler(private val response: Response = Response()) {


    fun handleGetRequest(request: String): String {
        return when (val path = getPath(request)) {
            "/" -> serveFile("/index.html")
            else -> serveFile(path)
        }
    }

    private fun serveFile(path: String): String {
        val responseBody = readFileContent(path)
        return response.generateResponse(responseBody,200, ContentType.HTML)
    }

    private fun readFileContent(fileName: String): String {
        val path = System.getProperty("user.dir")
        var file = File("$path/src/main/public$fileName")
        if (!file.exists()) {
            file = File("$path/src/main/public/404.html")
        }
        return file.readText(Charsets.UTF_8)
    }

    private fun getPath(request: String): String {
        return request.split("\r\n")[0].split(" ")[1].substringBefore("?")
    }


}