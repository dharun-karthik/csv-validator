package routeHandler.get.getErrors

import org.json.JSONObject
import request.RequestHandler
import response.ContentType
import response.Response

class GetError(val errors: JSONObject) {
    fun get(request: String): String {
        return try {
            getErrors(request)
        } catch (error: InvalidHeaderException) {
            properHeaderNotFoundError()
        }
    }

    private fun getErrors(request: String): String {
        val columnName = extractColumnName(request)
        val errorType = extractErrorType(request)
        println(errors)
        val columnError = errors.get(columnName) as JSONObject
        val columnErrorDetails = columnError.get("details") as JSONObject
        val errorLinesWithCount = columnErrorDetails.get(errorType) as JSONObject
        val errorLines = errorLinesWithCount.get("lines")
        println(errorLines)
        return errorLines.toString()
    }

    private fun extractErrorType(request: String): String {
        val requestHandler = RequestHandler()
        val errorType = requestHandler.getHeaderFieldValue(request, "error-type")
        if (errorType != null) {
            return errorType
        }
        throw InvalidHeaderException()
    }

    private fun extractColumnName(request: String): String {
        val requestHandler = RequestHandler()
        val id = requestHandler.getHeaderFieldValue(request, "column-name")
        if (id != null) {
            return id
        }
        throw InvalidHeaderException()
    }

    private fun properHeaderNotFoundError(): String {
        val response = Response()
        return response.generateResponse("Invalid Headers", 400, ContentType.HTML.value)
    }

}
