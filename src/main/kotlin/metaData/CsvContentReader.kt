package metaData

import org.json.JSONObject

class CsvContentReader(path: String) {

    private val headers: List<String>
    private val csvContent = FileReaderWriter(path)
    private val bufferedReader = csvContent.file.bufferedReader()
    private val regex: Regex
    private val expression = """(?:,|\n|^)("(?:(?:"")*[^"]*)*"|[^",\n]*|(?:\n|${'$'}))"""

    init {
        regex = expression.toRegex()
        headers = readHeader()
    }


    private fun readHeader(): List<String> {
        val head = bufferedReader.readLine()
        return head.split(regex)
    }


    fun readNextLineInJson(): JSONObject {
        val content = bufferedReader.readLine()
        val contentSplit = content.split(regex)
        val jsonObject = JSONObject()
        for (i in headers.indices) {
            jsonObject.put(headers[i], contentSplit[i])
        }
        return jsonObject
    }
}