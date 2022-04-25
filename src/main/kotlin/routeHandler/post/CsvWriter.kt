package routeHandler.post

import metaData.JsonContentReaderWriter
import org.json.JSONObject
import request.RequestHandler
import response.ContentType
import response.Response
import java.io.InputStream

class CsvWriter(private val jsonContentReaderWriter: JsonContentReaderWriter) {

    private val response = Response()
    private val requestHandler = RequestHandler()

    fun uploadCsvContent(request: String, inputStream: InputStream): String {
        val bodySize = requestHandler.getContentLength(request)
        val body = requestHandler.getBodyInString(bodySize, inputStream)
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