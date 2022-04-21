package metaData

import com.google.gson.Gson
import metaData.template.JsonMetaDataTemplate

class ConfigReaderWriter(path: String) : FileWriterReader(path) {
    fun appendField(data: String) {
        val gson = Gson()
        val fieldInJson = gson.fromJson(data, JsonMetaDataTemplate::class.java)
        val existingFields = readFields()
        val newFields = existingFields.plus(fieldInJson)
        writeConfigContent(newFields)
    }

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