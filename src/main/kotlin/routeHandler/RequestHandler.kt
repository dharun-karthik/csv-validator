package routeHandler

import metaData.MetaDataReaderWriter
import java.io.BufferedReader

class RequestHandler(
    metaDataReaderWriter: MetaDataReaderWriter
) {
    private val getRouteHandler = GetRouteHandler()
    private val postRouteHandler = PostRouteHandler(metaDataReaderWriter)
    private val deleteRouteHandler = DeleteRouteHandler(metaDataReaderWriter)
    private val pageNotFoundResponse = PageNotFoundResponse()

    fun handleRequest(request: String, inputStream: BufferedReader): String {
        return when (getRequestType(request)) {
            "GET" -> getRouteHandler.handleGetRequest(request)
            "POST" -> postRouteHandler.handlePostRequest(request, inputStream)
            "DELETE" -> deleteRouteHandler.handleDeleteRequest(request)
            else -> pageNotFoundResponse.handleUnknownRequest()
        }
    }

    private fun getRequestType(request: String): String {
        return request.substringBefore(" ")
    }
}