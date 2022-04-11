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
        val fieldArray = metaDataReaderWriter.readFields()
        val errorMessageForType = "Type Error in "
        val errorMessageForLength = "Length Error in "
        val errorMessageForDependency = "Dependency Error in "

        try {
            dataInJSONArray.forEachIndexed { index, element ->
                val fieldElement = (element as JSONObject)
                val keys = fieldElement.keySet()
                for (key in keys) {
                    if (typeValidation(fieldArray, key, fieldElement)) {
                        addError(index, errorMessageForType, key, arrayOfAllErrors[0])
                    }
                    if (lengthValidation(fieldArray, key, fieldElement)) {
                        addError(index, errorMessageForLength, key, arrayOfAllErrors[1])
                    }
                    if (dependencyValidation(fieldArray, key, fieldElement)) {
                        addError(index, errorMessageForDependency, key, arrayOfAllErrors[2])
                    }
                }
            }
        } catch (err: Exception) {
            println(err.message)
            return JSONArray()
        }
        return convertToSingleJsonArray(arrayOfAllErrors)
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
            (index + 1).toString(),
            "$errorMessage$key"
        )
        rowMap.put(jsonObject)
    }

    fun lengthValidation(
        metaDataList: Array<JsonMetaDataTemplate>,
        key: String,
        currentRow: JSONObject
    ): Boolean {
        val lengthValidation = LengthValidation()
        val field = metaDataList.first { it.fieldName == key }
        val isValid: Boolean
        val value = currentRow.get(key) as String

        isValid = (lengthTypeMap[LengthType.FIXED_LENGTH]!!.validateLengthType(
            value,
            field.length?.toInt(),
            lengthValidation
        ) &&
                lengthTypeMap[LengthType.MIN_LENGTH]!!.validateLengthType(
                    value,
                    field.minLength?.toInt(),
                    lengthValidation
                ) &&
                lengthTypeMap[LengthType.MAX_LENGTH]!!.validateLengthType(
                    value,
                    field.maxLength?.toInt(),
                    lengthValidation
                ))

        if (!isValid) {
            return true
        }
        return false
    }

    fun typeValidation(
        metaDataList: Array<JsonMetaDataTemplate>,
        key: String,
        currentRow: JSONObject,
    ): Boolean {
        val typeValidation = TypeValidation()
        val field = metaDataList.first { it.fieldName == key }
        val isValid: Boolean
        val value = currentRow.get(key) as String
        if (isFieldIsNull(value)) {
            return false
        }

        isValid = valueTypeMap[field.type]!!.validateValueType(value, typeValidation)

        if (!isValid) {
            return true
        }
        return false
    }

    fun dependencyValidation(
        metaDataList: Array<JsonMetaDataTemplate>,
        key: String,
        currentRow: JSONObject,
    ): Boolean {
        val dependencyValidation = DependencyValidation()
        val field = metaDataList.first { it.fieldName == key }
        val value = currentRow.get(key) as String
        if (field.dependencies != null) {
            val dependencies = field.dependencies
            for (dependency in dependencies) {
                val dependentValue = currentRow.get(dependency.dependentOn) as String
                val expectedCurrentValue = dependency.expectedCurrentFieldValue
                val expectedDependentValue = dependency.expectedDependentFieldValue
                val isValid: Boolean = dependencyValidation.validate(
                    value,
                    dependentValue,
                    expectedDependentValue,
                    expectedCurrentValue
                )
                if (!isValid) {
                    return true
                }
            }
        }
        return false
    }

    private fun isFieldIsNull(value: String?): Boolean {
        return value.contentEquals("null", ignoreCase = true)
    }
}