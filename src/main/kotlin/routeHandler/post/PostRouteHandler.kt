package routeHandler.post

import metaData.ConfigReaderWriter
import request.RequestHandler
import routeHandler.get.FileGetter
import java.io.BufferedReader


class PostRouteHandler(
    val configReaderWriter: ConfigReaderWriter,
) {
    private val requestHandler = RequestHandler()
    private val fileGetter = FileGetter()

    fun handlePostRequest(
        request: String,
        inputStream: BufferedReader,
    ): String {
        return when (requestHandler.getPath(request)) {
            "/csv" -> {
                val csvValidator = CsvValidator(configReaderWriter)
                csvValidator.handleCsv(request, inputStream)
            }
            "/add-meta-data" -> {
                val metaDataAdder = MetaDataAdder(configReaderWriter)
                metaDataAdder.handleAddCsvMetaData(request, inputStream)
            }
            else -> fileGetter.getFileNotFound()
        }
    }
}