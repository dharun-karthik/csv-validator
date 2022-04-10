package routeHandler

import metaData.MetaDataReaderWriter
import org.json.JSONArray
import org.json.JSONObject
import request.RequestHandle
import response.ContentType
import response.Response
import validation.*
import java.io.BufferedReader


class PostRouteHandler(
    val metaDataReaderWriter: MetaDataReaderWriter,
) {
    private val response = Response()
    private val requestHandle = RequestHandle()
    private val pageNotFoundResponse = PageNotFoundResponse()

    fun handlePostRequest(
        request: String,
        inputStream: BufferedReader,
    ): String {
        return when (requestHandle.getPath(request)) {
            "/csv" -> handleCsv(request, inputStream)
            "/add-meta-data" -> handleAddingCsvMetaData(request, inputStream)
            else -> pageNotFoundResponse.handleUnknownRequest()
        }
    }


    fun handleCsv(request: String, inputStream: BufferedReader): String {
        val bodySize = requestHandle.getContentLength(request)
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
        val bodySize = requestHandle.getContentLength(request)
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

}