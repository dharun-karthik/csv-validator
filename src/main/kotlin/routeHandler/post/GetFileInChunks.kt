package routeHandler.post

import metaData.ByteFileContent
import metaData.FileReaderWriter
import request.RequestHandler
import response.ContentType
import response.Response
import java.io.InputStream

class GetFileInChunks {
    val response = Response()
    fun handle(request: String, inputStream: InputStream): String {
        println(request)
        val requestHandler = RequestHandler()
        val contentLength = requestHandler.getContentRange(request)
            ?: return response.generateResponse("no content", 200, ContentType.HTML.value)
        val length = contentLength.rangeEnd - contentLength.rangeStart
        if (contentLength.rangeStart == 0) {
            ByteFileContent.initialiseByteArray(contentLength.size)
        }
        ByteFileContent.addBytes(contentLength,inputStream)
        if (contentLength.rangeEnd == contentLength.size) {
            val fileReaderWriter = FileReaderWriter("src/main/public/files/test.txt")
            val byteContent = ByteFileContent.getBytes()
            fileReaderWriter.writeBytes(byteContent)
            ByteFileContent.makeByteArrayEmpty()
        }
        return response.generateResponse("success", 200, ContentType.HTML.value)
    }

}