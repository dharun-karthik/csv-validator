package response

class Response {

    private val statusMap = mapOf(
        200 to "OK",
        201 to "Created",
        204 to "No Content",
        400 to "Bad Request",
        404 to "Not Found",
    )

    private val lineSeparator: String = System.lineSeparator()

    fun getHttpHead(statusCode: Int): String {
        val content = statusMap[statusCode]
        return "HTTP/1.1 $statusCode $content$lineSeparator"
    }

    fun generateResponse(content: String, statusCode: Int, contentType: String): String {
        val endOfHeader = lineSeparator + lineSeparator
        val contentLength = content.toByteArray().size
        return getHttpHead(statusCode) + generateContentType(contentType) + generateContentLength(contentLength) + endOfHeader + content
    }

    private fun generateContentType(contentType: String): String {
        return "Content-Type: ${contentType}; charset=utf-8" + lineSeparator
    }

    private fun generateContentLength(length: Int): String {
        return "Content-Length: $length"
    }

    fun onlyHeaderResponse(statusCode: Int): String {
        return getHttpHead(statusCode) + lineSeparator
    }
}