package routeHandler.post

import metaData.ConfigFileReaderWriter
import metaData.JsonContentReaderWriter
import request.RequestHandler
import routeHandler.get.FileGetter
import java.io.InputStream


class PostRouteHandler {
    private val requestHandler = RequestHandler()
    private val fileGetter = FileGetter()

    fun handlePostRequest(
        request: String,
        inputStream: InputStream,
    ): String {
        return when (requestHandler.getPath(request)) {
            "/csv" -> {
                val jsonContentReaderWriter = JsonContentReaderWriter("src/main/public/files/content.json")
                val csvWriter = CsvWriter(jsonContentReaderWriter)
                csvWriter.uploadCsvContent(request, inputStream)
            }
            "/add-meta-data" -> {
                val configFileReaderWriter = ConfigFileReaderWriter("src/main/public/files/csv-config.json")
                val configWriter = ConfigWriter(configFileReaderWriter)
                configWriter.handleWriteConfigData(request, inputStream)
            }
            "/test/file-in-chunks" -> {
                val getFileInChunks = GetFileInChunks()
                getFileInChunks.handle(request, inputStream)
            }
            else -> fileGetter.getFileNotFound()
        }
    }
}