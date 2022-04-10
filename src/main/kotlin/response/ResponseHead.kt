package response

class ResponseHead {

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
}