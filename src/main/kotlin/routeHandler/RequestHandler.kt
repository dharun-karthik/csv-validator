package routeHandler

import java.io.BufferedReader

class RequestHandler {
    private val getRouteHandler = GetRouteHandler()
    private val pageNotFoundResponse = PageNotFoundResponse()

    val postRouteHandler = PostRouteHandler()

    fun handleRequest(request: String, inputStream: BufferedReader): String {
        return when (getRequestType(request)) {
            "GET" -> getRouteHandler.handleGetRequest(request)
            "POST" -> postRouteHandler.handlePostRequest(request, inputStream)
            else -> pageNotFoundResponse.handleUnknownRequest()
        }
    }

    private fun getRequestType(request: String): String {
        return request.substringBefore(" ")
    }
}