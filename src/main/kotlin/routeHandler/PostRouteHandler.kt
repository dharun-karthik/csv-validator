package routeHandler

import metaData.MetaDataReaderWriter
import request.RequestHandle
import routeHandler.post.MetaDataAdder
import routeHandler.post.CsvValidator
import java.io.BufferedReader


class PostRouteHandler(
    val metaDataReaderWriter: MetaDataReaderWriter,
) {
    private val requestHandle = RequestHandle()
    private val pageNotFoundResponse = PageNotFoundResponse()

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
            else -> pageNotFoundResponse.handleUnknownRequest()
        }
    }
}