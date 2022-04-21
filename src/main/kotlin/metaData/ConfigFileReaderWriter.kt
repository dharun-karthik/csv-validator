package metaData

import com.google.gson.Gson
import metaData.template.JsonMetaDataTemplate

class ConfigFileReaderWriter(path: String) : FileReaderWriter(path) {
    fun readFields(): Array<JsonMetaDataTemplate> {
        val data = readRawContent()
        val gson = Gson()
        return gson.fromJson(data, Array<JsonMetaDataTemplate>::class.java) ?: return arrayOf()
    }

    fun writeConfigContent(jsonData: Array<JsonMetaDataTemplate>) {
        val gson = Gson()
        val stringData = gson.toJson(jsonData, Array<JsonMetaDataTemplate>::class.java)
        writeRawContent(stringData)
    }

    fun clearFields() {
        writeRawContent("[]")
    }
}