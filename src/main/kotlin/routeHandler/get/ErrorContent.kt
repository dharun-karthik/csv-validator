package routeHandler.get

import org.json.JSONArray


object ErrorContent {
    private var errorContent: JSONArray? = null

    fun setError(jsonArray: JSONArray){
        errorContent = jsonArray
    }

    fun getErrors(): JSONArray? {
        return errorContent
    }
}