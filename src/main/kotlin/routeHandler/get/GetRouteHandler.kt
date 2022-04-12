package routeHandler.get

import request.RequestHandle
import response.ContentType
import response.Response
import java.io.File

class GetRouteHandler {
    private val requestHandle = RequestHandle()
    private val fileGetter = FileGetter()

    fun handleGetRequest(request: String): String {
        return when (val path = requestHandle.getPath(request)) {
            "/" -> fileGetter.serveFile("/index.html")
            "/get-meta-data" -> fileGetter.serveFile("/csv-meta-data.json")
            else -> fileGetter.serveFile(path)
        }
    }

}