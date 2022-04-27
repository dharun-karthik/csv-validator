package metaData

import org.json.JSONObject

class CsvContentReader(path: String) {

    private val headers: List<String>
    private val csvContent = FileReaderWriter(path)
    private val bufferedReader = csvContent.file.bufferedReader()

    init {
        headers = readHeader()
    }


    private fun readHeader(): List<String> {
        val head = bufferedReader.readLine()
        val csvSplitter = CsvSplitter(head)
        return csvSplitter.getAllValues()
    }


    fun readNextLineInJson(): JSONObject {
        val content = bufferedReader.readLine()
        val csvSplitter = CsvSplitter(content)
        val jsonObject = JSONObject()
        for(head in headers){
            jsonObject.put(head,csvSplitter.getNextValue())
        }
        return jsonObject
    }
}