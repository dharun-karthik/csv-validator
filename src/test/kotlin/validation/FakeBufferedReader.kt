package validation

import java.io.BufferedReader
import java.io.Reader

class FakeBufferedReader(val data: String, reader: Reader = Reader.nullReader()) : BufferedReader(reader) {
    override fun read(charArray: CharArray): Int {
        for (i in charArray.indices) {
            charArray[i] = data[i]
        }
        return charArray.size
    }
}