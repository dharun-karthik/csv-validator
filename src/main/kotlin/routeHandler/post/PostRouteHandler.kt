package routeHandler.post

import metaData.MetaDataReaderWriter
import request.RequestHandle
import routeHandler.get.FileGetter
import java.io.BufferedReader


class PostRouteHandler(
    val metaDataReaderWriter: MetaDataReaderWriter,
) {
    private val requestHandle = RequestHandle()
    private val fileGetter = FileGetter()

    fun handlePostRequest(
        request: String,
        inputStream: BufferedReader,
    ): String {
        return when (requestHandle.getPath(request)) {
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