package routeHandler

import java.io.BufferedReader
import java.io.InputStream

class InputStreamProvider(
    private val inputStream: InputStream
) {
    private val bufferedReader = inputStream.bufferedReader()

    fun getBufferedReader(): BufferedReader {
        return bufferedReader
    }

    fun getByteStream(): InputStream {
        return inputStream
    }
}