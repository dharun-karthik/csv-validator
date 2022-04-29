package fakeStreams

import java.io.BufferedReader
import java.io.Reader

class FakeBufferedReader(private val data: String, reader: Reader = Reader.nullReader()) : BufferedReader(reader) {
    override fun read(charArray: CharArray): Int {
        for (i in charArray.indices) {
            charArray[i] = data[i]
        }
        return charArray.size
    }
}