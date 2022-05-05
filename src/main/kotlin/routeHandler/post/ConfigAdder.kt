package routeHandler.post

import com.google.gson.Gson
import db.DBConfigReaderWriter
import metaData.template.JsonConfigTemplate
import org.json.JSONObject
import request.RequestHandler
import response.ContentType
import response.Response
import utils.InputStreamProvider

class ConfigAdder {
    fun handle(request: String, inputStreamProvider: InputStreamProvider): String {
        val requestHandler = RequestHandler()
        val bodySize = requestHandler.getContentLength(request)
        val body = requestHandler.getBodyInString(bodySize, inputStreamProvider.getBufferedReader())
        val jsonBody = JSONObject(body)
        val jsonKeys = jsonBody.keys()
        val configName = jsonKeys.next()
        val arrayContent = jsonBody.getJSONArray(configName).toString()
        val parsedContent = Gson().fromJson(arrayContent, Array<JsonConfigTemplate>::class.java)
        val dbConfigReaderWriter = DBConfigReaderWriter()
        dbConfigReaderWriter.writeConfig(configName, parsedContent)
        return Response().generateResponse("success", 200, ContentType.HTML.value)
    }
}
