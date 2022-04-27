package metaData

import com.google.gson.Gson
import metaData.template.JsonConfigTemplate

class ConfigFileReaderWriter(path: String) : FileReaderWriter(path) {
    fun readFields(): Array<JsonConfigTemplate> {
        val data = readRawContent()
        val gson = Gson()
        return gson.fromJson(data, Array<JsonConfigTemplate>::class.java) ?: return arrayOf()
    }

    fun writeConfigContent(jsonData: Array<JsonConfigTemplate>) {
        val gson = Gson()
        val stringData = gson.toJson(jsonData, Array<JsonConfigTemplate>::class.java)
        writeRawContent(stringData)
    }

    fun clearFields() {
        writeRawContent("[]")
    }
}