package routeHandler.post

import metaData.ConfigReaderWriter
import metaData.JsonContentReaderWriter
import request.RequestHandler
import routeHandler.get.FileGetter
import java.io.BufferedReader


class PostRouteHandler{
    private val requestHandler = RequestHandler()
    private val fileGetter = FileGetter()

    fun handlePostRequest(
        request: String,
        inputStream: BufferedReader,
    ): String {
        return when (requestHandler.getPath(request)) {
            "/csv" -> {
                val jsonContentReaderWriter = JsonContentReaderWriter("src/main/public/files/content.json")
                val csvWriter = CsvWriter(jsonContentReaderWriter)
                csvWriter.uploadCsvContent(request, inputStream)
            }
            "/add-meta-data" -> {
                val configReaderWriter = ConfigReaderWriter("src/main/public/files/csv-config.json")
                val csvValidator = CsvValidator(configReaderWriter)
                csvValidator.handleAddCsvMetaData(request, inputStream)
            }
            else -> fileGetter.getFileNotFound()
        }
    }
}