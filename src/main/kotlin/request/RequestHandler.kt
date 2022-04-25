package request

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class RequestHandler {
    fun getPath(request: String): String {
        return request.split("\r\n")[0].split(" ")[1].substringBefore("?")
    }

    fun getContentLength(request: String): Int {
        request.split("\n").forEach { headerString ->
            val keyValue = headerString.split(":", limit = 2)
            if (keyValue[0].lowercase().contains("content-length")) {
                return keyValue[1].trim().toInt()
            }
        }
        return 0
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