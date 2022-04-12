package validation

import lengthValidator.*
import metaData.DependencyTemplate
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
        "AlphaNumeric" to AlphaNumeric(), "Alphabet" to Alphabet(), "Number" to Numbers()
    )

    fun validate(dataInJSONArray: JSONArray): JSONArray {
        val arrayOfAllErrors = List(4) { JSONArray() }
        val metaDataList = metaDataReaderWriter.readFields()
        iterateJsonContent(dataInJSONArray, metaDataList, arrayOfAllErrors)
        return convertToSingleJsonArray(arrayOfAllErrors)
    }

    private fun iterateJsonContent(
        dataInJSONArray: JSONArray, metaDataList: Array<JsonMetaDataTemplate>, arrayOfAllErrors: List<JSONArray>
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
                if(!restrictedInputValidation(metaDataField,currentFieldValue)){
                    addError(index,getErrorMessage("Foreign Value Found"),key,arrayOfAllErrors[2])
                }
                if (!dependencyValidation(metaDataField, currentFieldValue, currentRow)) {
                    addError(index, getErrorMessage("Dependency"), key, arrayOfAllErrors[3])
                }
            }
        }
    }

    private fun restrictedInputValidation(metaDataField: JsonMetaDataTemplate, currentFieldValue: String): Boolean {
        val restrictedInputValidation = RestrictedInputValidation()
        val restrictedInputList = metaDataField.values ?: return true
        return restrictedInputValidation.validate(currentFieldValue,restrictedInputList)
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
            getLineMessageWithKey(index + 1), "$errorMessage$key"
        )
        rowMap.put(jsonObject)
    }

    fun lengthValidation(
        metaDataField: JsonMetaDataTemplate, currentFieldValue: String
    ): Boolean {
        val lengthValidation = LengthValidation()
        if (isFieldIsNull(currentFieldValue)) {
            return true
        }
        return lengthCheck(currentFieldValue, metaDataField, lengthValidation)
    }

    private fun lengthCheck(
        currentFieldValue: String, metaDataField: JsonMetaDataTemplate, lengthValidation: LengthValidation
    ): Boolean {
        val isValid = (checkFixedLength(currentFieldValue, metaDataField, lengthValidation) && checkMinLength(
            currentFieldValue,
            metaDataField,
            lengthValidation
        ) && checkMaxLength(currentFieldValue, metaDataField, lengthValidation))

        if (!isValid) {
            return false
        }
        return true
    }

    private fun checkMaxLength(
        currentFieldValue: String, metaDataField: JsonMetaDataTemplate, lengthValidation: LengthValidation
    ) = lengthTypeMap[LengthType.MAX_LENGTH]!!.validateLengthType(
        currentFieldValue, metaDataField.maxLength?.toInt(), lengthValidation
    )

    private fun checkMinLength(
        currentFieldValue: String, metaDataField: JsonMetaDataTemplate, lengthValidation: LengthValidation
    ) = lengthTypeMap[LengthType.MIN_LENGTH]!!.validateLengthType(
        currentFieldValue, metaDataField.minLength?.toInt(), lengthValidation
    )

    private fun checkFixedLength(
        currentFieldValue: String, metaDataField: JsonMetaDataTemplate, lengthValidation: LengthValidation
    ) = lengthTypeMap[LengthType.FIXED_LENGTH]!!.validateLengthType(
        currentFieldValue, metaDataField.length?.toInt(), lengthValidation
    )

    fun typeValidation(
        metaDataField: JsonMetaDataTemplate, currentFieldValue: String
    ): Boolean {
        val typeValidation = TypeValidation()
        if (isFieldIsNull(currentFieldValue)) {
            return true
        }

        return checkType(metaDataField, currentFieldValue, typeValidation)
    }

    private fun checkType(
        metaDataField: JsonMetaDataTemplate,
        currentFieldValue: String,
        typeValidation: TypeValidation
    ): Boolean {
        val isValid = valueTypeMap[metaDataField.type]!!.validateValueType(currentFieldValue, typeValidation)

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
            if (checkDependency(dependencies, currentRow, dependencyValidation, currentFieldValue)){
                return false
            }
        }
        return true
    }

    private fun checkDependency(
        dependencies: List<DependencyTemplate>,
        currentRow: JSONObject,
        dependencyValidation: DependencyValidation,
        currentFieldValue: String
    ): Boolean {
        for (dependency in dependencies) {
            val dependentValue = currentRow.get(dependency.dependentOn) as String
            val expectedCurrentValue = dependency.expectedCurrentFieldValue
            val expectedDependentValue = dependency.expectedDependentFieldValue

            val isValid = dependencyValidation.validate(
                currentFieldValue, dependentValue, expectedDependentValue, expectedCurrentValue
            )

            if (!isValid) {
                return true
            }
        }
        return false
    }

    private fun isFieldIsNull(value: String?): Boolean {
        return value.contentEquals("null", ignoreCase = true)
    }
}