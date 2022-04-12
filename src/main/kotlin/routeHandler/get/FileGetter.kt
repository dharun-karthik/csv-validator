package routeHandler.get

import response.ContentType
import response.Response
import java.io.File

class FileGetter {
    private val response: Response = Response()
    private val contentTypeMap = mapOf(
        "html" to "text/html",
        "json" to "application/json",
        "css" to "text/css",
        "js" to "text/javascript",
        "ico" to "image/vnd.microsoft.icon",
        "svg" to "image/svg+xml"
    )

    fun serveFile(path: String): String {
        if (!isFileExists(path)) {
            return getFileNotFound()
        }
        val contentType = getContentTypeForFile(path)
        val responseBody = readFileContent(path)
        return response.generateResponse(responseBody, 200, contentType)
    }

    fun getFileNotFound(): String {
        val responseBody = readFileContent("/pages/404.html")
        return response.generateResponse(responseBody, 404, ContentType.HTML.value)
    }

    private fun readFileContent(fileName: String): String {
        val file = getFile(fileName)
        return file.readText(Charsets.UTF_8)
    }

    private fun isFileExists(fileName: String): Boolean {
        val file = getFile(fileName)
        return file.exists()
    }

    private fun getFile(fileName: String): File {
        val path = System.getProperty("user.dir")
        return File("$path/src/main/public$fileName")
    }

    private fun getContentTypeForFile(fileName: String): String {
        val file = getFile(fileName)
        val fileFormat = file.name.split(".").last()
        return getMatchingContentType(fileFormat)
    }

    private fun getMatchingContentType(fileFormat: String): String {
        return contentTypeMap[fileFormat] ?: return "text/plain"
    }
}