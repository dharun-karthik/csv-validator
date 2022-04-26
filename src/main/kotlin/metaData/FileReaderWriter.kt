package metaData

import java.io.File

open class FileReaderWriter(
    private val path: String
) {
    val file = getMetaDataFile()

    fun readRawContent(): String {
        return file.readText()
    }

    fun writeRawContent(data: String) {
        file.writeText(data)
    }

    private fun getMetaDataFile(): File {
        val currentFile = File(path)
        if (!currentFile.exists()) {
            currentFile.createNewFile()
        }
        return currentFile
    }

    fun writeBytes(byteArray: ByteArray) {
        file.writeBytes(byteArray)
    }

}