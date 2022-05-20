package utils

import java.io.BufferedReader
import java.io.InputStream

open class InputStreamProvider(
    private val inputStream: InputStream
) {
    private val bufferedReader = inputStream.bufferedReader()

    open fun getBufferedReader(): BufferedReader {
        return bufferedReader
    }

    open fun getByteStream(): InputStream {
        return inputStream
    }
}