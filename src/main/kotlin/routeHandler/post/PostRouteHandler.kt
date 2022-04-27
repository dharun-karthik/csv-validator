package routeHandler.post

import metaData.ConfigFileReaderWriter
import metaData.FileReaderWriter
import metaData.CsvContentReader
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
            "/add-meta-data" -> {
                val configFileReaderWriter = ConfigFileReaderWriter("src/main/public/files/csv-config.json")
                val configWriter = ConfigWriter(configFileReaderWriter)
                configWriter.handleWriteConfigData(request, getBufferedReader(inputStream))
            }
            "/test/upload-csv" -> {
                val fileReaderWriter = FileReaderWriter("src/main/public/files/uploaded.csv")
                val csvFileDownloader = CsvFileDownloader(fileReaderWriter)
                csvFileDownloader.handle(request, inputStream)
            }
            else -> fileGetter.getFileNotFound()
        }
    }

    private fun getBufferedReader(inputStream: InputStream): BufferedReader {
        return BufferedReader(InputStreamReader(inputStream))
    }
}