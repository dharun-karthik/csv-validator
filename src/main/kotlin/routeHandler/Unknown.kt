package routeHandler

class Unknown(private val responseHead : ResponseHead = ResponseHead()) {

    fun handleUnknownRequest(): String {
        return responseHead.getHttpHead(400) + "\r\n\r\n"
    }

}