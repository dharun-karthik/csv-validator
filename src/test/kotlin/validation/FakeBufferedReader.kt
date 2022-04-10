package validation

import java.io.BufferedReader
import java.io.Reader

class FakeBufferedReader(reader: Reader, val data: String) : BufferedReader(reader) {
    override fun read(charArray: CharArray): Int {
        for(i in charArray.indices){
            charArray[i] = data[i]
        }
        return charArray.size
    }
}