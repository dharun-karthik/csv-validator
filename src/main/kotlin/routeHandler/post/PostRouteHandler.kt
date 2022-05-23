package routeHandler.post

import metaData.ConfigFileReaderWriter
import metaData.FileReaderWriter
import request.RequestHandler
import routeHandler.get.FileGetter
import utils.InputStreamProvider


class PostRouteHandler {
    private val requestHandler = RequestHandler()
    private val fileGetter = FileGetter()

    fun handlePostRequest(
        request: String,
        inputStreamProvider: InputStreamProvider,
    ): String {
        return when (requestHandler.getPath(request)) {
            "/add-meta-data" -> {
                val configFileReaderWriter = ConfigFileReaderWriter("src/main/public/files/csv-config.json")
                val configWriter = ConfigWriter(configFileReaderWriter)
                configWriter.uploadValidConfigJson(request, inputStreamProvider)
            }
            "/upload-csv" -> {
                val fileReaderWriter = FileReaderWriter("src/main/public/files/uploaded.csv")
                val csvFileDownloader = CsvFileDownloader(fileReaderWriter)
                csvFileDownloader.handle(request, inputStreamProvider)
            }
            "/add-config" -> {
                val configAdder = ConfigAdder()
                configAdder.handle(request, inputStreamProvider)
            }
            else -> fileGetter.getFileNotFound()
        }
    }
}