package metaData

object ByteFileContent {
    private var byteArray: ByteArray? = null
    private var currentIndex = 0

    fun initialiseByteArray(size: Int) {
        byteArray = ByteArray(size)
    }

    fun addBytes(byteData: ByteArray) {
        for (byte in byteData) {
            byteArray?.set(currentIndex++, byte)
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