package routeHandler.get

import db.DBConfigReaderWriter
import org.json.JSONArray
import request.RequestHandler
import response.ContentType
import response.Response

class GetConfigByName {
    fun handle(request: String): String {
        val requestHandler = RequestHandler()
        val response = Response()
        val configName = requestHandler.getHeaderFieldValue(request, "config-name")
            ?: return response.generateResponse("No config name", 400, ContentType.HTML.value)
        val body = DBConfigReaderWriter().readConfig(configName)
        val bodyInJsonFormat = JSONArray(body)
        val bodyInStringFormat = bodyInJsonFormat.toString()
        return response.generateResponse(bodyInStringFormat, 200, ContentType.JSON.value)
    }

}
