package routeHandler.post

import metaData.ByteFileContent
import metaData.FileReaderWriter
import request.ContentRange
import request.RequestHandler
import response.ContentType
import response.Response
import java.io.InputStream

class CsvFileDownloader(
    private val fileReaderWriter: FileReaderWriter
) {
    val response = Response()
    fun handle(request: String, inputStream: InputStream): String {
        val requestHandler = RequestHandler()
        val contentRange = requestHandler.getContentRange(request)
            ?: return response.generateResponse("no content", 200, ContentType.HTML.value)
        handleFileDownload(contentRange, inputStream)
        return response.generateResponse("success", 200, ContentType.HTML.value)
    }

    private fun handleFileDownload(contentRange: ContentRange, inputStream: InputStream) {
        if (contentRange.rangeStart == 0) {
            ByteFileContent.initialiseByteArray(contentRange.size)
        }
        ByteFileContent.addBytes(contentRange, inputStream)
        if (contentRange.rangeEnd == contentRange.size) {
            val byteContent = ByteFileContent.getBytes()
            fileReaderWriter.writeBytes(byteContent)
            ByteFileContent.makeByteArrayEmpty()
        }
    }

}