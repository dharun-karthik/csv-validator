package validation

import lengthValidator.*
import metaData.JsonMetaDataTemplate
import metaData.MetaDataReaderWriter
import org.json.JSONArray
import org.json.JSONObject
import valueValidator.AlphaNumeric
import valueValidator.Alphabet
import valueValidator.Numbers
import valueValidator.ValueTypeValidator

class Validation(private val metaDataReaderWriter: MetaDataReaderWriter) {
    private val lengthTypeMap: Map<LengthType, LengthTypeValidator> = mapOf(
        LengthType.FIXED_LENGTH to FixedLength(),
        LengthType.MIN_LENGTH to MinLength(),
        LengthType.MAX_LENGTH to MaxLength()
    )
    private val valueTypeMap: Map<String, ValueTypeValidator> = mapOf(
        "AlphaNumeric" to AlphaNumeric(),
        "Alphabet" to Alphabet(),
        "Number" to Numbers()
    )

    fun validate(dataInJSONArray: JSONArray): JSONArray {
        val arrayOfAllErrors = List(3) { JSONArray() }
        val metaDataList = metaDataReaderWriter.readFields()
        iterateJsonContent(dataInJSONArray, metaDataList, arrayOfAllErrors)
        return convertToSingleJsonArray(arrayOfAllErrors)
    }

    private fun iterateJsonContent(
        dataInJSONArray: JSONArray,
        metaDataList: Array<JsonMetaDataTemplate>,
        arrayOfAllErrors: List<JSONArray>
    ) {
        dataInJSONArray.forEachIndexed { index, element ->
            val currentRow = (element as JSONObject)
            val keys = currentRow.keySet()
            for (key in keys) {
                val metaDataField = metaDataList.first { it.fieldName.contentEquals(key, ignoreCase = true) }
                val currentFieldValue = currentRow.get(key) as String

                if (!typeValidation(metaDataField, currentFieldValue)) {
                    addError(index, getErrorMessage("Type"), key, arrayOfAllErrors[0])
                }
                if (!lengthValidation(metaDataField, currentFieldValue)) {
                    addError(index, getErrorMessage("Length"), key, arrayOfAllErrors[1])
                }
                if (!dependencyValidation(metaDataField, currentFieldValue, currentRow)) {
                    addError(index, getErrorMessage("Dependency"), key, arrayOfAllErrors[2])
                }
            }
        }
    }

    private fun getLineMessageWithKey(index: Int): String {
        return "Line Number $index"
    }

    private fun getErrorMessage(errorType: String): String {
        return "$errorType Error in "
    }

    private fun convertToSingleJsonArray(arrayOfAllErrors: List<JSONArray>): JSONArray {
        val rowMap = JSONArray()
        for (errors in arrayOfAllErrors) {
            rowMap.putAll(errors)
        }
        return rowMap
    }

    private fun addError(index: Int, errorMessage: String, key: String?, rowMap: JSONArray) {
        val jsonObject = JSONObject().put(
            getLineMessageWithKey(index + 1),
            "$errorMessage$key"
        )
        rowMap.put(jsonObject)
    }

    fun lengthValidation(
        metaDataField: JsonMetaDataTemplate,
        currentFieldValue: String
    ): Boolean {
        val lengthValidation = LengthValidation()
        if (isFieldIsNull(currentFieldValue)) {
            return true
        }

        val isValid: Boolean = (lengthTypeMap[LengthType.FIXED_LENGTH]!!.validateLengthType(
            currentFieldValue,
            metaDataField.length?.toInt(),
            lengthValidation
        ) &&
                lengthTypeMap[LengthType.MIN_LENGTH]!!.validateLengthType(
                    currentFieldValue,
                    metaDataField.minLength?.toInt(),
                    lengthValidation
                ) &&
                lengthTypeMap[LengthType.MAX_LENGTH]!!.validateLengthType(
                    currentFieldValue,
                    metaDataField.maxLength?.toInt(),
                    lengthValidation
                ))

        if (!isValid) {
            return false
        }
        return true
    }

    fun typeValidation(
        metaDataField: JsonMetaDataTemplate,
        currentFieldValue: String
    ): Boolean {
        val typeValidation = TypeValidation()
        if (isFieldIsNull(currentFieldValue)) {
            return true
        }

        val isValid: Boolean = valueTypeMap[metaDataField.type]!!.validateValueType(currentFieldValue, typeValidation)

        if (!isValid) {
            return false
        }
        return true
    }

    private fun dependencyValidation(
        metaDataField: JsonMetaDataTemplate,
        currentFieldValue: String,
        currentRow: JSONObject,
    ): Boolean {
        val dependencyValidation = DependencyValidation()
        if (metaDataField.dependencies != null) {
            val dependencies = metaDataField.dependencies
            for (dependency in dependencies) {
                val dependentValue = currentRow.get(dependency.dependentOn) as String
                val expectedCurrentValue = dependency.expectedCurrentFieldValue
                val expectedDependentValue = dependency.expectedDependentFieldValue

                val isValid: Boolean = dependencyValidation.validate(
                    currentFieldValue,
                    dependentValue,
                    expectedDependentValue,
                    expectedCurrentValue
                )

                if (!isValid) {
                    return false
                }
            }
        }
        return true
    }

    private fun isFieldIsNull(value: String?): Boolean {
        return value.contentEquals("null", ignoreCase = true)
    }
}