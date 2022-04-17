package routeHandler.post

import metaData.MetaDataReaderWriter
import request.RequestHandler
import routeHandler.get.FileGetter
import java.io.BufferedReader


class PostRouteHandler(
    val metaDataReaderWriter: MetaDataReaderWriter,
) {
    private val requestHandler = RequestHandler()
    private val fileGetter = FileGetter()

    fun handlePostRequest(
        request: String,
        inputStream: BufferedReader,
    ): String {
        return when (requestHandler.getPath(request)) {
            "/csv" -> {
                val csvValidator = CsvValidator(metaDataReaderWriter)
                csvValidator.handleCsv(request, inputStream)
            }
            "/add-meta-data" -> {
                val metaDataAdder = MetaDataAdder(metaDataReaderWriter)
                metaDataAdder.handleAddCsvMetaData(request, inputStream)
            }
            else -> fileGetter.getFileNotFound()
        }
    }
}