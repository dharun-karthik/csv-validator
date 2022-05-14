package routeHandler.get.getErrors

import org.json.JSONObject

object ErrorContent {
    private var errorContent: JSONObject? = null

    fun setError(jsonObject: JSONObject) {
        errorContent = jsonObject
    }

    fun getErrors(): JSONObject? {
        return errorContent
    }
}