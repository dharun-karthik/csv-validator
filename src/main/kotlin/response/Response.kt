package response

class Response {

    private val statusMap = mapOf(
        200 to "Found",
        400 to "Bad Request",
        401 to "Unauthorized",
        204 to "No Content",
    )

    fun getHttpHead(statusCode: Int): String {
        val content = statusMap[statusCode]
        return "HTTP/1.1 $statusCode $content\n"
    }

    fun generateResponse(content: String, statusCode: Int, contentType: ContentType): String {
        val endOfHeader = "\r\n\r\n"
        val contentLength = content.length
        return getHttpHead(statusCode) + """Content-Type: $contentType; charset=utf-8
            |Content-Length: $contentLength""".trimMargin() + endOfHeader + content
    }
}