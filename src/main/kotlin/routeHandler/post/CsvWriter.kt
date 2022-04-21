package routeHandler.post

import metaData.JsonContentReaderWriter
import org.json.JSONObject
import request.RequestHandler
import response.ContentType
import response.Response
import java.io.BufferedReader

class CsvWriter(val jsonContentReaderWriter: JsonContentReaderWriter) {

    private val response = Response()
    private val requestHandler = RequestHandler()

    fun uploadCsvContent(request: String, inputStream: BufferedReader): String {
        val bodySize = requestHandler.getContentLength(request)
        val body = requestHandler.getBody(bodySize, inputStream)
        writeContent(body)
        val responseBody = getResponse()
        return response.generateResponse(responseBody, 200, ContentType.JSON.value)
    }

    private fun getResponse(): String {
        val responseBody = JSONObject()
        responseBody.put("value", "Success")
        return responseBody.toString()
    }

    private fun writeContent(body: String) {
        jsonContentReaderWriter.writeRawContent(body)
    }


}