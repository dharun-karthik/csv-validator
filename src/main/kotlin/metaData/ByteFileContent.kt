package metaData

import request.ContentRange
import java.io.InputStream

object ByteFileContent {
    private var byteArray: ByteArray? = null
    private var currentIndex = 0

    fun initialiseByteArray(size: Int) {
        byteArray = ByteArray(size)
    }

    fun addBytes(contentLength: ContentRange, inputStream: InputStream) {
        for (i in contentLength.rangeStart until contentLength.rangeEnd) {
            byteArray?.set(i, inputStream.read().toByte())
        }
    }

    fun makeByteArrayEmpty() {
        byteArray = null
        currentIndex = 0
    }

    fun size(): Int {
        return byteArray?.size ?: 0
    }

    fun getBytes(): ByteArray {
        if (byteArray == null) {
            println("no content in the byte array")
            return ByteArray(2)
        }
        return byteArray as ByteArray
    }

}