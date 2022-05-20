package fakeStreams

import java.io.InputStream

class FakeInputStream(private val data: String) : InputStream() {
    var counter = 0

    override fun read(): Int {
        return data[counter++].code
    }
}