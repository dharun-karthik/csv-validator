package routeHandler.post

import metaData.ConfigFileReaderWriter
import org.json.JSONObject
import request.RequestHandler
import response.ContentType
import response.Response
import routeHandler.InputStreamProvider

class ConfigWriter(val configFileReaderWriter: ConfigFileReaderWriter) {
    private val response = Response()
    private val requestHandler = RequestHandler()

    fun handleWriteConfigData(request: String, inputStreamProvider: InputStreamProvider): String {
        val bodySize = requestHandler.getContentLength(request)
        val body = requestHandler.getBodyInString(bodySize, inputStreamProvider.getBufferedReader())
        return addCsvMetaData(body)
    }

    private fun addCsvMetaData(body: String): String {
        configFileReaderWriter.writeRawContent(body)
        val responseBody = JSONObject()
        responseBody.put("value", "Success")
        return response.generateResponse(responseBody.toString(), 201, ContentType.JSON.value)
    }

}