package metaData

import JsonMetaDataTemplate
import com.google.gson.Gson
import java.io.File

class MetaDataWriter(
    val path: String
) {
    val file = getMetaDataFile()

    fun appendField(data: String) {
        readFields()
    }

    fun readFields(): Array<JsonMetaDataTemplate>? {
        val data = readRawContent()
        val gson = Gson()
        return gson.fromJson(data, Array<JsonMetaDataTemplate>::class.java)
    }

    fun readRawContent(): String {
        return file.readText()
    }

    private fun getMetaDataFile(): File {
        val currentFile = File(path)
        if (!currentFile.exists()) {
            currentFile.createNewFile()
        }
        return currentFile
    }
}