package metaData

import JsonMetaDataTemplate
import com.google.gson.Gson
import java.io.File

class MetaDataWriter(
    val path: String
) {
    val file = getMetaDataFile()

    fun appendField(data: String) {
        val gson = Gson()
        val fieldInJson = gson.fromJson(data,JsonMetaDataTemplate::class.java)
        val existingFields = readFields()
        val newFields = existingFields?.plus(fieldInJson)
        if (newFields != null) {
            writeJsonContent(newFields)
        }
    }

    fun readFields(): Array<JsonMetaDataTemplate>? {
        val data = readRawContent()
        val gson = Gson()
        return gson.fromJson(data, Array<JsonMetaDataTemplate>::class.java)
    }

    fun readRawContent(): String {
        return file.readText()
    }

    fun writeJsonContent(jsonData : Array<JsonMetaDataTemplate>){
        val gson = Gson()
        val stringData = gson.toJson(jsonData,Array<JsonMetaDataTemplate>::class.java)
        file.writeText(stringData)
    }

    private fun getMetaDataFile(): File {
        val currentFile = File(path)
        if (!currentFile.exists()) {
            currentFile.createNewFile()
        }
        return currentFile
    }
}