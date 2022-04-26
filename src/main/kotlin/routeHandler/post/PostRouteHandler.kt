package routeHandler.post

import metaData.ConfigFileReaderWriter
import metaData.JsonContentReaderWriter
import request.RequestHandler
import routeHandler.get.FileGetter
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


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
                csvWriter.uploadCsvContent(request, getBufferedReader(inputStream))
            }
            "/add-meta-data" -> {
                val configFileReaderWriter = ConfigFileReaderWriter("src/main/public/files/csv-config.json")
                val configWriter = ConfigWriter(configFileReaderWriter)
                configWriter.handleWriteConfigData(request, getBufferedReader(inputStream))
            }
            "/test/file-in-chunks" -> {
                val getFileInChunks = GetFileInChunks()
                getFileInChunks.handle(request, inputStream)
            }
            else -> fileGetter.getFileNotFound()
        }
    }

    private fun getBufferedReader(inputStream: InputStream): BufferedReader {
        return BufferedReader(InputStreamReader(inputStream))
    }
}