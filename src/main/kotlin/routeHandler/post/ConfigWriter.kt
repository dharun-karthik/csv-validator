package routeHandler.post

import metaData.ConfigFileReaderWriter
import org.json.JSONArray
import org.json.JSONObject
import request.RequestHandler
import response.ContentType
import response.Response
import routeHandler.InputStreamProvider
import validation.ConfigJsonValidator

class ConfigWriter(val configFileReaderWriter: ConfigFileReaderWriter) {
    private val response = Response()
    private val requestHandler = RequestHandler()
    private val configValidator = ConfigJsonValidator()

    fun uploadValidConfigJson(request: String, inputStreamProvider: InputStreamProvider): String {
        val bodySize = requestHandler.getContentLength(request)
        val body = requestHandler.getBodyInString(bodySize, inputStreamProvider.getBufferedReader())
        val validationError = configValidator.validate(body)
        if (!validationError.isEmpty) {
            return errorResponse(validationError)
        }
        return addCsvMetaData(body)
    }

    private fun addCsvMetaData(body: String): String {
        configFileReaderWriter.writeRawContent(body)
        return successResponse()
    }

    private fun successResponse(): String {
        val responseBody = JSONObject()
        responseBody.put("value", "Success")
        return response.generateResponse(responseBody.toString(), 201, ContentType.JSON.value)
    }

    private fun errorResponse(errors: JSONArray): String {
        return response.generateResponse(errors.toString(), 400, ContentType.JSON.value)
    }

}