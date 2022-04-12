package routeHandler

import metaData.MetaDataReaderWriter
import request.RequestHandle
import response.Response
import routeHandler.get.FileGetter

class DeleteRouteHandler(
    private val metaDataReaderWriter: MetaDataReaderWriter,
    private val response: Response = Response()
) {
    private val fileGetter = FileGetter()
    private val requestHandler = RequestHandle()
    fun handleDeleteRequest(request: String): String {
        return when (requestHandler.getPath(request)) {
            "/reset-config" -> deleteMetaData()
            else -> return fileGetter.getFileNotFound()
        }
    }

    private fun deleteMetaData(): String {
        metaDataReaderWriter.clearFields()
        return response.onlyHeaderResponse(204)
    }
}
