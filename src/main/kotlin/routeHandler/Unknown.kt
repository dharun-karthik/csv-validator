package routeHandler

class Unknown {

    private val statusMap = mapOf(
        200 to "Found",
        400 to "Bad Request",
        401 to "Unauthorized"
    )

    fun handleUnknownRequest(): String {
        return getHttpHead(400) + "\r\n\r\n"
    }

    private fun getHttpHead(statusCode: Int): String {
        val content = statusMap[statusCode]
        return "HTTP/1.1 $statusCode $content\n"
    }
}