package routeHandler.post

import metaData.ConfigReaderWriter
import org.json.JSONArray
import request.RequestHandler
import response.Response
import validation.implementation.ColumnValidation
import java.io.BufferedReader

class CsvValidator(val configReaderWriter: ConfigReaderWriter) {
    private val response = Response()
    private val requestHandler = RequestHandler()

    fun handleAddCsvMetaData(request: String, inputStream: BufferedReader): String {
        val bodySize = requestHandler.getContentLength(request)
        val body = requestHandler.getBody(bodySize, inputStream)
        return addCsvMetaData(body)
    }

    private fun addCsvMetaData(body: String): String {
////        configReaderWriter.appendField(body)
////        val jsonBody = JSONArray(body)
////
////        val errorColumnsJson = getErrorColumns(jsonBody)
////        if (!errorColumnsJson.isEmpty) {
////            return response.generateResponse(errorColumnsJson.toString(), 200, ContentType.JSON.value)
////        }
////
////        val validation = Validation(configReaderWriter)
////        val responseBody = validation.validate(jsonBody)
//        return response.generateResponse(responseBody.toString(), 201, ContentType.JSON.value)
        TODO("handle meta data and send validated response")
    }

    private fun getErrorColumns(jsonBody: JSONArray): JSONArray {
        val columnValidation = ColumnValidation()
        val metaDataFields = configReaderWriter.readRawContent()
        return columnValidation.getColumnsNotInConfig(metaDataFields, jsonBody)
    }
}