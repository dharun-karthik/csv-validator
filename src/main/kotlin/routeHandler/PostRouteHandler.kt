package routeHandler

import JsonMetaDataTemplate
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import validation.DuplicationValidation
import java.io.BufferedReader

class PostRouteHandler(
    var fieldArray: Array<JsonMetaDataTemplate> = arrayOf(),
    private val responseHead : ResponseHead = ResponseHead()
) {
    private val pageNotFoundResponse = PageNotFoundResponse()

    fun handlePostRequest(request: String, inputStream: BufferedReader): String {

        return when (getPath(request)) {
            "/csv" -> handleCsv(request, inputStream)
            "/add-meta-data" -> handleAddingCsvMetaData(request, inputStream)
            else -> pageNotFoundResponse.handleUnknownRequest()
        }
    }


    private fun getPath(request: String): String {
        return request.split("\r\n")[0].split(" ")[1].substringBefore("?")
    }

    fun handleCsv(request: String, inputStream: BufferedReader): String {
        val bodySize = getContentLength(request)
        val body = getBody(bodySize, inputStream)
        println("body $body")
        val jsonBody = JSONArray(body)

        val repeatedRowList = DuplicationValidation().getDuplicateRowNumberInJSON(jsonBody)
        println("Repeated Lines :$repeatedRowList")
        val typeValidationResultList = typeValidation(jsonBody)
        val lengthValidationResultList = lengthValidation(jsonBody)
        var responseBody = ""
        responseBody += "{"
        responseBody = if (!repeatedRowList.isEmpty()) {
            "\"Repeated Lines\" : \"$repeatedRowList\""
        } else {
            "No Error"
        }
        responseBody += "}"
        val contentLength = responseBody.length
        val endOfHeader = "\r\n\r\n"
        return responseHead.getHttpHead(200) + """Content-Type: text/json; charset=utf-8
            |Content-Length: $contentLength""".trimMargin() + endOfHeader + responseBody
    }


    fun handleAddingCsvMetaData(request: String, inputStream: BufferedReader): String {
        println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&")
        val bodySize = getContentLength(request)
        val body = getBody(bodySize, inputStream)
        println(body)
        return addCsvMetaData(body)
    }

    fun addCsvMetaData(body: String): String {
        val jsonBody = getMetaData(body)
        println(body)
        fieldArray = jsonBody
        val endOfHeader = "\r\n\r\n"
        val responseBody = "Successfully Added"
        val contentLength = responseBody.length
        return responseHead.getHttpHead(200) + """Content-Type: text/plain; charset=utf-8
            |Content-Length: $contentLength""".trimMargin() + endOfHeader + responseBody
    }

    private fun getBody(bodySize: Int, inputStream: BufferedReader): String {
        val buffer = CharArray(bodySize)
        inputStream.read(buffer)
        return String(buffer)
    }

    fun getMetaData(body: String): Array<JsonMetaDataTemplate> {
        val gson = Gson()
        return gson.fromJson(body, Array<JsonMetaDataTemplate>::class.java)
    }


    private fun getContentLength(request: String): Int {
        request.split("\n").forEach { headerString ->
            val keyValue = headerString.split(":", limit = 2)
            if (keyValue[0].contains("Content-Length")) {
                return keyValue[1].trim().toInt()
            }
        }
        return 0
    }


    private fun lengthValidation(dataInJSONArray: JSONArray): List<Int> {
        val rowList = mutableListOf<Int>()
        dataInJSONArray.forEachIndexed { index, element ->
            println("$index $element")
        }
        return mutableListOf()
    }

    fun typeValidation(dataInJSONArray: JSONArray): List<Int> {
        val rowList = mutableListOf<Int>()
        dataInJSONArray.forEachIndexed { index, element ->
            println(element)
            val keys = (element as JSONObject).keySet()
            for (key in keys) {
                println("key $key index $index")
                val field = fieldArray.first { it.fieldName == key }
                val type = field.type
                rowList.add(index+1)
            }
        }
        return mutableListOf()
    }

}