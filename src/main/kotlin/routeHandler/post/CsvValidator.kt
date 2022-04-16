package routeHandler.post

import metaData.MetaDataReaderWriter
import org.json.JSONArray
import request.RequestHandle
import response.ContentType
import response.Response
import validation.implementation.ColumnValidation
import validation.Validation
import java.io.BufferedReader

class CsvValidator(val metaDataReaderWriter: MetaDataReaderWriter) {

    private val response = Response()
    private val requestHandle = RequestHandle()

    fun handleCsv(request: String, inputStream: BufferedReader): String {
        val bodySize = requestHandle.getContentLength(request)
        val body = requestHandle.getBody(bodySize, inputStream)
        val jsonBody = JSONArray(body)

        val errorColumnsJson = getErrorColumns(jsonBody)
        if (!errorColumnsJson.isEmpty) {
            return response.generateResponse(errorColumnsJson.toString(), 200, ContentType.JSON.value)
        }

        val validation = Validation(metaDataReaderWriter)
        val responseBody = validation.validate(jsonBody)

        return response.generateResponse(responseBody.toString(), 200, ContentType.JSON.value)
    }

    private fun getErrorColumns(jsonBody: JSONArray): JSONArray {
        val columnValidation = ColumnValidation()
        val metaDataFields = metaDataReaderWriter.readRawContent()
        return columnValidation.getColumnsNotInConfig(metaDataFields, jsonBody)
    }

}