package routeHandler

import JsonMetaDataTemplate
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import response.ResponseHead
import validation.DuplicationValidation
import validation.LengthValidation
import validation.TypeValidation
import java.io.BufferedReader

class PostRouteHandler(
    var fieldArray: Array<JsonMetaDataTemplate> = arrayOf(),
    private val responseHead: ResponseHead = ResponseHead()
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
        println("Repeated Lines :$repeatedRowList , ${repeatedRowList.isEmpty}")
        val typeValidationResultList = typeValidation(jsonBody)
        val lengthValidationResultList = lengthValidation(jsonBody)
        var responseBody = "{"
        responseBody +=
            if (!repeatedRowList.isEmpty || typeValidationResultList.isNotEmpty() || lengthValidationResultList.isNotEmpty()) {
                "\"Repeated Lines\" : \"$repeatedRowList\"\n" +
                        "\"Type Error Lines\" : \"$typeValidationResultList\"\n" +
                        "\"Length Error Lines\" : \"$lengthValidationResultList\"\n"

            } else {
                "\"Response\" : \"No Error\""
            }
        responseBody += "}"
        println(responseBody)
        val contentLength = responseBody.length
        val endOfHeader = "\r\n\r\n"
        return responseHead.getHttpHead(200) + """Content-Type: text/json; charset=utf-8
            |Content-Length: $contentLength""".trimMargin() + endOfHeader + responseBody
    }


    fun handleAddingCsvMetaData(request: String, inputStream: BufferedReader): String {
        val bodySize = getContentLength(request)
        val body = getBody(bodySize, inputStream)
        return addCsvMetaData(body)
    }

    fun addCsvMetaData(body: String): String {
        val jsonBody = getMetaData(body)
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


    fun lengthValidation(dataInJSONArray: JSONArray): List<Int> {
        val rowList = mutableListOf<Int>()
        val lengthValidation = LengthValidation()
        try {
            dataInJSONArray.forEachIndexed { index, element ->
                val filedElement = (element as JSONObject)
                val keys = filedElement.keySet()
                for (key in keys) {
                    val field = fieldArray.first { it.fieldName == key }
                    var isValid = true
                    val value = filedElement.get(key) as String
                    if (field.length != null) {
                        if (!lengthValidation.fixedLength(value, field.length)) {
                            println("1 ${field.length}, $value")
                            isValid = false
                        }
                    }
                    if (field.maxLength != null) {
                        if (!lengthValidation.maxLength(value, field.maxLength)) {
                            println("2 ${field.maxLength}, $value")
                            isValid = false
                        }
                    }
                    if (field.minLength != null) {
                        if (!lengthValidation.minLength(value, field.minLength)) {
                            println("3 ${field.minLength}, $value")
                            isValid = false
                        }
                    }
                    if (!isValid) {
                        rowList.add(index + 1)
                        break
                    }
                }
            }
        } catch (err: Exception) {
            return listOf()
        }
        return rowList
    }

    fun typeValidation(dataInJSONArray: JSONArray): List<Int> {
        val rowList = mutableListOf<Int>()
        val typeValidation = TypeValidation()
        try {
            dataInJSONArray.forEachIndexed { index, element ->
                println(element)
                val ele = (element as JSONObject)
                val keys = ele.keySet()
                for (key in keys) {
                    println("key $key")
                    val field = fieldArray.first { it.fieldName == key }
                    var isValid = true
                    val value = ele.get(key) as String
                    println("${field.type} , $key , $value")
                    if (field.type == "AlphaNumeric" && !typeValidation.isAlphaNumeric(value)) {
                        isValid = false
                    } else if (field.type == "Alphabet" && !typeValidation.isAlphabetic(value)) {
                        isValid = false
                    } else if (field.type == "Number" && !typeValidation.isNumeric(value)) {
                        isValid = false
                    }
                    if (!isValid) {
                        rowList.add(index + 1)
                        continue
                    }
                }
            }
        } catch (err: Exception) {
            return listOf()
        }
        return rowList
    }
}