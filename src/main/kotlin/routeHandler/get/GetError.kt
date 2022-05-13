package routeHandler.get

import org.json.JSONObject
import request.RequestHandler
import response.ContentType
import response.Response

class GetError {
    fun get(request: String): String {
        try {
            getErrors(request)
        } catch (e: Exception) {
            return properHeaderNotFoundError()
        }
//        errorLines.get()
        return "hello"
    }

    private fun getErrors(request: String) {
        val id = extractColumnId(request)
        val currentRange = extractRange(request)
        val errorType = extractErrorType(request)
        val errors = ErrorContent.getErrors()
//        val errorLines = errors?.get(id) as JSONObject
        TODO()
    }

    private fun extractErrorType(request: String): String {
        val requestHandler = RequestHandler()
        TODO()
//        val errorType = requestHandler.getHeaderFieldValue(request, "error-type")
//        return errorType
    }

    private fun extractRange(request: String): ErrorRange {
        val requestHandler = RequestHandler()
        val errorRange = requestHandler.getHeaderFieldValue(request, "error-range")
        if (errorRange != null) {
            val split = errorRange.split("-")
            return ErrorRange(split[0].toInt(), split[1].toInt())
        }
        return ErrorRange(0, 0)
    }

    private fun extractColumnId(request: String): Int? {
        val requestHandler = RequestHandler()
        val id = requestHandler.getHeaderFieldValue(request, "id")
        TODO()
//        if (id != null) {
//            return id.toInt()
//        }
    }

    private fun properHeaderNotFoundError(): String {
        val response = Response()
        return response.generateResponse("Invalid Headers", 400, ContentType.HTML.value)
    }

}
