package routeHandler.get

import com.google.gson.JsonArray
import db.DBConnection
import response.ContentType
import response.Response

class ConfigName {

    private val response = Response()

    fun getConfigNames(): String {
        val query = "SELECT config_name FROM csv_configuration"
        val statement = DBConnection.getDBConnection().createStatement()
        val result = statement.executeQuery(query)
        val listOfConfigNames = JsonArray()
        while (result.next()) {
            listOfConfigNames.add(result.getString("config_name"))
        }
        return response.generateResponse(listOfConfigNames.toString(), 200, ContentType.JSON.value)
    }
}