package routeHandler

class Unknown {

    val responseHead = ResponseHead()

    fun handleUnknownRequest(): String {
        return responseHead.getHttpHead(400) + "\r\n\r\n"
    }

}