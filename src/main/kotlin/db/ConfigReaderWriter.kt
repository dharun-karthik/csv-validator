package db

import com.google.gson.Gson
import metaData.template.JsonConfigTemplate

class ConfigReaderWriter {
    private val gson = Gson()

    fun readConfig(configName: String): Array<JsonConfigTemplate> {
        val statement = DBConnection.createStatement()
        val result = statement.executeQuery("SELECT * FROM fields")
        while (result.next()) {
            println(result.getString("field_name"))
        }
        val data = ""
        return gson.fromJson(data, Array<JsonConfigTemplate>::class.java) ?: return arrayOf()
    }

    fun addConfig(configName: String, jsonData: Array<JsonConfigTemplate>) {
        val stringData = gson.toJson(jsonData, Array<JsonConfigTemplate>::class.java)
    }
}