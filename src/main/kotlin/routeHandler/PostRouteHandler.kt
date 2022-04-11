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
        val body = requestHandle.getBody(bodySize, inputStream)
        val jsonBody = JSONArray(body)

        val errorColumnsJson = getErrorColumns(jsonBody)
        if(!errorColumnsJson.isEmpty){
            return response.generateResponse(errorColumnsJson.toString(),200,ContentType.JSON)
        }

        val repeatedRowList = DuplicationValidation().getDuplicateRowNumberInJSON(jsonBody)
        val validation = Validation(metaDataReaderWriter)
        val responseBody = repeatedRowList.putAll(validation.validate(jsonBody))

        return response.generateResponse(responseBody.toString(),200,ContentType.JSON)
    }

    private fun getErrorColumns(jsonBody: JSONArray): JSONArray {
        val columnValidation = ColumnValidation()
        val metaDataFields = metaDataReaderWriter.readRawContent()
        return columnValidation.getInvalidFieldNames(metaDataFields, jsonBody)
    }


    fun handleAddingCsvMetaData(request: String, inputStream: BufferedReader): String {
        val bodySize = requestHandle.getContentLength(request)
        val body = requestHandle.getBody(bodySize, inputStream)
        return addCsvMetaData(body)
    }

    fun addCsvMetaData(body: String): String {
        metaDataReaderWriter.appendField(body)
        val responseBody = JSONObject()
        responseBody.put("value","Success")
        return response.generateResponse(responseBody.toString(),200,ContentType.JSON)
    }
}