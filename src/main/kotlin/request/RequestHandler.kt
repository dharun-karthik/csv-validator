package request

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class RequestHandler {
    fun getPath(request: String): String {
        return request.split("\r\n")[0].split(" ")[1].substringBefore("?")
    }

    fun getContentLength(request: String): Int {
        val value = getHeaderFieldValue(request, "content-length")
        if (value != null) {
            return value.trim().toInt()
        }
        return 0
    }

    fun getContentRange(request: String): ContentRange? {
        val value = getHeaderFieldValue(request, "content-range")
        if (value != null) {
            return getContentRangeFromValue(value)
        }
        return null
    }

    private fun getContentRangeFromValue(value: String): ContentRange {
        val splitValue = value.split(" ")
        val unit = splitValue[0]
        val rangeSplit = splitValue[1].split("-")
        val rangeStart = rangeSplit[0].trim().toInt()
        val endAndSizeSplit = rangeSplit[1].split("/")
        val rangeEnd = endAndSizeSplit[0].trim().toInt()
        val size = endAndSizeSplit[1].trim().toInt()
        return ContentRange(unit, rangeStart, rangeEnd, size)
    }

    private fun getHeaderFieldValue(request: String, headerField: String): String? {
        request.split("\n").forEach { headerString ->
            val keyValue = headerString.split(":", limit = 2)
            if (keyValue[0].lowercase().contains(headerField)) {
                return keyValue[1]
            }
        }
        return null
    }

    fun getBodyInString(bodySize: Int, inputStream: InputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val buffer = CharArray(bodySize)
        bufferedReader.read(buffer)
        return String(buffer)
    }

    fun getBodyInBytes(bodySize: Int, inputStream: InputStream): ByteArray {
        val buffer = ByteArray(bodySize)
        inputStream.read(buffer)
        return buffer
    }

}