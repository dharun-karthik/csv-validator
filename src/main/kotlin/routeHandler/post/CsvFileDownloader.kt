package routeHandler.post

import metaData.FileReaderWriter
import request.ContentRange
import request.RequestHandler
import response.ContentType
import response.Response
import utils.ByteFileContent
import utils.InputStreamProvider

class CsvFileDownloader(
    private val fileReaderWriter: FileReaderWriter
) {
    val response = Response()
    fun handle(request: String, inputStreamProvider: InputStreamProvider): String {
        val requestHandler = RequestHandler()
        val contentRange = requestHandler.getContentRange(request)
            ?: return response.generateResponse("no content", 200, ContentType.HTML.value)
        handleFileDownload(contentRange, inputStreamProvider)
        return response.generateResponse("success", 200, ContentType.HTML.value)
    }

    private fun handleFileDownload(contentRange: ContentRange, inputStreamProvider: InputStreamProvider) {
        if (contentRange.rangeStart == 0) {
            ByteFileContent.initialiseByteArray(contentRange.size)
        }
        ByteFileContent.addBytes(contentRange, inputStreamProvider.getByteStream())
        if (contentRange.rangeEnd == contentRange.size) {
            val byteContent = ByteFileContent.getBytes()
            fileReaderWriter.writeBytes(byteContent)
            ByteFileContent.makeByteArrayEmpty()
        }
    }

}