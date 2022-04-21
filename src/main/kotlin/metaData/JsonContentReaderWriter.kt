package metaData

import org.json.JSONArray

class JsonContentReaderWriter(path: String) : FileReaderWriter(path) {

    fun readJsonData(): JSONArray {
        val rawData = readRawContent()
        return JSONArray(rawData)
    }
}