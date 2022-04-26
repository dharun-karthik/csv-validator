package routeHandler.post

import metaData.FileReaderWriter
import request.RequestHandler
import response.ContentType
import response.Response
import java.io.InputStream

class GetFileInChunks {
    fun handle(request: String, inputStream: InputStream): String {
        val fileReaderWriter = FileReaderWriter("src/main/public/files/test.csv")
        val requestHandler = RequestHandler()
        val contentLength = requestHandler.getContentLength(request)
        println("length $contentLength")
        val data = requestHandler.getBodyInBytes(contentLength, inputStream)
        fileReaderWriter.appendRawBytes(data)
        val response = Response()
        return response.generateResponse("success", 200, ContentType.HTML.value)
    }

}