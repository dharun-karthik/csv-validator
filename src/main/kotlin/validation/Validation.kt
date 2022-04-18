package validation

import metaData.JsonMetaDataTemplate
import metaData.MetaDataReaderWriter
import org.json.JSONArray
import org.json.JSONObject
import validation.implementation.DuplicationValidation
import validation.operation.DependencyValidationOperation
import validation.operation.LengthValidationOperation
import validation.operation.RestrictedInputValidationOperation
import validation.operation.TypeValidationOperation
import validation.operation.ValidationType.*

class Validation(private val metaDataReaderWriter: MetaDataReaderWriter) {
    private val validationMap = mutableMapOf(
        TYPE_VALIDATION to TypeValidationOperation(),
        LENGTH_VALIDATION to LengthValidationOperation(),
        RESTRICTED_INPUT_VALIDATION to RestrictedInputValidationOperation(),
        DEPENDENCY_VALIDATION to DependencyValidationOperation()
    )

    fun validate(dataInJSONArray: JSONArray): JSONArray {
        val metaDataList = metaDataReaderWriter.readFields()
        return iterateJsonContent(dataInJSONArray, metaDataList)
    }

    private fun iterateJsonContent(
        dataInJSONArray: JSONArray, metaDataList: Array<JsonMetaDataTemplate>
    ): JSONArray {
        val arrayOfAllErrorsByLine = JSONArray()
        val duplicationValidation = DuplicationValidation()
        dataInJSONArray.forEachIndexed { index, element ->
            val lineErrors = mutableMapOf<String, MutableList<String>>()
            val currentRow = (element as JSONObject)
            val keys = currentRow.keySet()
            appendDuplicationError(duplicationValidation, currentRow, index, lineErrors)
            appendValidationErrors(keys, metaDataList, currentRow, lineErrors)
            val lineNumberInCsv = index + 2
            if (lineErrors.isNotEmpty()) {
                val singleLineErrors = parseErrorsIntoSingleJson(lineNumberInCsv, lineErrors)
                arrayOfAllErrorsByLine.put(singleLineErrors)
            }
        }
        return arrayOfAllErrorsByLine
    }

    private fun appendDuplicationError(
        duplicationValidation: DuplicationValidation,
        currentRow: JSONObject,
        index: Int,
        lineErrors: MutableMap<String, MutableList<String>>
    ) {
        val previousDuplicateIndex = duplicationValidation.isDuplicateIndexAvailable(currentRow, index)
        if (previousDuplicateIndex != null) {
            val name = "Row Duplication Error"
            val errorList = lineErrors.getOrPut(name) { mutableListOf() }
            errorList.add((previousDuplicateIndex + 1).toString())
        }
    }

    private fun appendValidationErrors(
        keys: MutableSet<String>,
        metaDataList: Array<JsonMetaDataTemplate>,
        currentRow: JSONObject,
        lineErrors: MutableMap<String, MutableList<String>>
    ) {
        for (key in keys) {
            appendValidationErrorForCurrentField(metaDataList, key, currentRow, lineErrors)
        }
    }

    private fun appendValidationErrorForCurrentField(
        metaDataList: Array<JsonMetaDataTemplate>,
        key: String,
        currentRow: JSONObject,
        lineErrors: MutableMap<String, MutableList<String>>
    ) {
        val metaDataField = metaDataList.first { it.fieldName.contentEquals(key, ignoreCase = true) }
        val currentFieldValue = currentRow.get(key) as String

        validationMap.forEach { entry ->
            val validationType = entry.value
            if (!validationType.validate(metaDataField, currentFieldValue, currentRow)) {
                val validationTypeName = entry.key
                val errorList = lineErrors.getOrPut(validationTypeName.validationErrorName) { mutableListOf() }
                errorList.add("$key : $currentFieldValue")
            }
        }
    }

    private fun parseErrorsIntoSingleJson(index: Int, lineErrors: MutableMap<String, MutableList<String>>): JSONObject {
        return JSONObject().put(index.toString(), lineErrors)
    }
}