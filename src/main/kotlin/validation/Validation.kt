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
        "alphanumeric" to AlphaNumeric(), "alphabets" to Alphabet(), "number" to Numbers()
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
            val lineErrors = mutableListOf<String>()
            val currentRow = (element as JSONObject)
            val keys = currentRow.keySet()
            val previousDuplicateIndex = duplicationValidation.isDuplicateIndexAvailable(currentRow, index)
            if (previousDuplicateIndex != null) {
                lineErrors.add("Row Duplication From $previousDuplicateIndex")
            }
            for (key in keys) {
                val metaDataField = metaDataList.first { it.fieldName.contentEquals(key, ignoreCase = true) }
                val currentFieldValue = currentRow.get(key) as String

                if (!typeValidation(metaDataField, currentFieldValue)) {
                    lineErrors.add(getErrorMessage("Type", key))
                }
                if (!lengthValidation(metaDataField, currentFieldValue)) {
                    lineErrors.add(getErrorMessage("Length", key))
                }
                if (!restrictedInputValidation(metaDataField, currentFieldValue)) {
                    lineErrors.add(getErrorMessage("Value Not Found", key))
                }
                if (!dependencyValidation(metaDataField, currentFieldValue, currentRow)) {
                    lineErrors.add(getErrorMessage("Dependency", key))
                }
            }
            if (lineErrors.isNotEmpty()) {
                val singleLineErrors = parseErrorsIntoSingleJson(index + 1, lineErrors)
                arrayOfAllErrorsByLine.put(singleLineErrors)
            }
        }
        return arrayOfAllErrorsByLine
    }

    private fun parseErrorsIntoSingleJson(index: Int, lineErrors: MutableList<String>): JSONObject {
        return JSONObject().put(index.toString(), lineErrors)
    }

    private fun restrictedInputValidation(metaDataField: JsonMetaDataTemplate, currentFieldValue: String): Boolean {
        val restrictedInputValidation = RestrictedInputValidation()
        val restrictedInputList = metaDataField.values ?: return true
        return restrictedInputValidation.validate(currentFieldValue, restrictedInputList)
    }

    private fun getErrorMessage(errorType: String, key: String?): String {
        return "$errorType Error in $key"
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
            currentFieldValue, metaDataField, lengthValidation
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
        metaDataField: JsonMetaDataTemplate, currentFieldValue: String, typeValidation: TypeValidation
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
            if (checkDependency(dependencies, currentRow, dependencyValidation, currentFieldValue)) {
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
            val dependentValue = currentRow.getString(dependency.dependentOn)
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