package fakeStreams

import utils.InputStreamProvider
import java.io.BufferedReader
import java.io.InputStream

class FakeInputStreamProvider(private val data: String) : InputStreamProvider(InputStream.nullInputStream()) {
    override fun getBufferedReader(): BufferedReader {
        return FakeBufferedReader(data)
    }

    override fun getByteStream(): InputStream {
        return FakeInputStream(data)
    }
}