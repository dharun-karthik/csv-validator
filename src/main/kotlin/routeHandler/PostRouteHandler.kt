package routeHandler

import metaData.MetaDataReaderWriter
import org.json.JSONArray
import org.json.JSONObject
import response.ContentType
import response.Response
import validation.*
import java.io.BufferedReader


class PostRouteHandler(
    val metaDataReaderWriter: MetaDataReaderWriter,
) {
    private val response = Response()
    private val pageNotFoundResponse = PageNotFoundResponse()

    fun handlePostRequest(
        request: String,
        inputStream: BufferedReader,
    ): String {
        return when (getPath(request)) {
            "/csv" -> handleCsv(request, inputStream)
            "/add-meta-data" -> handleAddingCsvMetaData(request, inputStream)
            else -> pageNotFoundResponse.handleUnknownRequest()
        }
    }

    private fun getPath(request: String): String {
        return request.split("\r\n")[0].split(" ")[1].substringBefore("?")
    }

    fun handleCsv(request: String, inputStream: BufferedReader): String {
        val bodySize = getContentLength(request)
        val body = getBody(bodySize, inputStream)
        val jsonBody = JSONArray(body)

        val repeatedRowList = DuplicationValidation().getDuplicateRowNumberInJSON(jsonBody)
        val validation = Validation(metaDataReaderWriter)
        val typeValidationResultList = validation.typeValidation(jsonBody)
        val lengthValidationResultList = validation.lengthValidation(jsonBody)
        val dependencyValidation = validation.dependencyValidation(jsonBody)
        val responseBody =
            repeatedRowList.putAll(typeValidationResultList).putAll(lengthValidationResultList)
                .putAll(dependencyValidation)

        return response.generateResponse(responseBody.toString(),200,ContentType.JSON)
    }


    fun handleAddingCsvMetaData(request: String, inputStream: BufferedReader): String {
        val bodySize = getContentLength(request)
        val body = getBody(bodySize, inputStream)
        return addCsvMetaData(body)
    }

    fun addCsvMetaData(body: String): String {
        metaDataReaderWriter.appendField(body)
        val responseBody = JSONObject()
        responseBody.put("value","Success")
        return response.generateResponse(responseBody.toString(),200,ContentType.JSON)
    }

    private fun getBody(bodySize: Int, inputStream: BufferedReader): String {
        val buffer = CharArray(bodySize)
        inputStream.read(buffer)
        return String(buffer)
    }

    private fun getContentLength(request: String): Int {
        request.split("\n").forEach { headerString ->
            val keyValue = headerString.split(":", limit = 2)
            if (keyValue[0].contains("Content-Length")) {
                return keyValue[1].trim().toInt()
            }
        }
        return 0
    }

}