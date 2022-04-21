package routeHandler.get

import request.RequestHandler

class GetRouteHandler {
    private val requestHandler = RequestHandler()
    private val fileGetter = FileGetter()

    fun handleGetRequest(request: String): String {
        return when (val path = requestHandler.getPath(request)) {
            "/" -> fileGetter.serveFile("/index.html")
            "/get-meta-data" -> fileGetter.serveFile("/files/csv-config.json")
            else -> fileGetter.serveFile(path)
        }
    }

}