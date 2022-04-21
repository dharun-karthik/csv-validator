package routeHandler.get

import metaData.ConfigFileReaderWriter
import metaData.JsonContentReaderWriter
import request.RequestHandler

class GetRouteHandler {
    private val requestHandler = RequestHandler()
    private val fileGetter = FileGetter()

    fun handleGetRequest(request: String): String {
        return when (val path = requestHandler.getPath(request)) {
            "/" -> fileGetter.serveFile("/index.html")
            "/get-meta-data" -> fileGetter.serveFile("/files/csv-config.json")
            "/validate" -> {
                val configFileReaderWriter = ConfigFileReaderWriter("src/main/public/files/csv-config.json")
                val jsonContentReaderWriter = JsonContentReaderWriter("src/main/public/files/content.json")
                CsvValidator(configFileReaderWriter, jsonContentReaderWriter).handleCsv()
            }
            else -> fileGetter.serveFile(path)
        }
    }

}