package routeHandler.get

import metaData.ConfigFileReaderWriter
import metaData.JsonContentReaderWriter
import org.json.JSONArray
import response.ContentType
import response.Response
import validation.Validation
import validation.implementation.ColumnValidation

class CsvValidator(
    val configFileReaderWriter: ConfigFileReaderWriter,
    private val jsonContentReaderWriter: JsonContentReaderWriter
) {

    private val response = Response()

    fun handleCsv(): String {
        val csvContent = jsonContentReaderWriter.readJsonData()

        val errorColumnsJson = getErrorColumns(csvContent)
        if (!errorColumnsJson.isEmpty) {
            return response.generateResponse(errorColumnsJson.toString(), 200, ContentType.JSON.value)
        }

        val validation = Validation(configFileReaderWriter)
        val responseBody = validation.validate(csvContent)

        return response.generateResponse(responseBody.toString(), 200, ContentType.JSON.value)
    }

    private fun getErrorColumns(jsonBody: JSONArray): JSONArray {
        val columnValidation = ColumnValidation()
        val metaDataFields = configFileReaderWriter.readRawContent()
        return columnValidation.getColumnsNotInConfig(metaDataFields, jsonBody)
    }
}