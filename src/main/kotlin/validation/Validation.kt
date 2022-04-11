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
        val typeValidation = TypeValidation()
        val lengthValidation = LengthValidation()
        val dependencyValidation = DependencyValidation()
        val fieldArray = metaDataReaderWriter.readFields()
        val errorMessageForType = "Type Error in "
        val errorMessageForLength = "Length Error in "
        val errorMessageForDependency = "Dependency Error in "

        try {
            dataInJSONArray.forEachIndexed { index, element ->
                val fieldElement = (element as JSONObject)
                val keys = fieldElement.keySet()
                for (key in keys) {
                    if (typeVal(fieldArray, key, fieldElement, typeValidation)) {
                        addError(index, errorMessageForType, key, arrayOfAllErrors[0])
                    }
                    if (lengthVal(fieldArray, key, fieldElement, lengthValidation)) {
                        addError(index, errorMessageForLength, key, arrayOfAllErrors[1])
                    }
                    if (depVal(fieldArray, key, fieldElement, dependencyValidation)) {
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


    //Todo isolate common code between length and type validation
    //todo single responsibility
    //todo eliminate if else
    fun lengthValidation(dataInJSONArray: JSONArray): JSONArray {
        val rowMap = JSONArray()
        val lengthValidation = LengthValidation()
        val fieldArray = metaDataReaderWriter.readFields()
        val errorMessage = "Length Error in "
        try {
            dataInJSONArray.forEachIndexed { index, element ->
                val fieldElement = (element as JSONObject)
                val keys = fieldElement.keySet()
                for (key in keys) {
                    if (lengthVal(fieldArray, key, fieldElement, lengthValidation)) {
                        val jsonObject = JSONObject().put(
                            (index + 1).toString(),
                            "$errorMessage$key"
                        )
                        rowMap.put(jsonObject)
                    }
                }
            }
        } catch (err: Exception) {
            println(err.message)
            return JSONArray()
        }
        return rowMap
    }

    private fun lengthVal(
        fieldArray: Array<JsonMetaDataTemplate>,
        key: String?,
        fieldElement: JSONObject,
        lengthValidation: LengthValidation
    ): Boolean {
        val field = fieldArray.first { it.fieldName == key }
        val isValid: Boolean
        val value = fieldElement.get(key) as String

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

    fun typeValidation(dataInJSONArray: JSONArray): JSONArray {
        val rowMap = JSONArray()
        val typeValidation = TypeValidation()
        val fieldArray = metaDataReaderWriter.readFields()
        val errorMessage = "Type Error in "
        try {
            dataInJSONArray.forEachIndexed { index, element ->
                val fieldElement = (element as JSONObject)
                val keys = fieldElement.keySet()
                for (key in keys) {
                    if (typeVal(fieldArray, key, fieldElement, typeValidation)) {
                        val jsonObject = JSONObject().put(
                            (index + 1).toString(),
                            "$errorMessage$key"
                        )
                        rowMap.put(jsonObject)
                    }
                }
            }
        } catch (err: Exception) {
            println(err.message)
            return JSONArray()
        }
        return rowMap
    }

    private fun typeVal(
        fieldArray: Array<JsonMetaDataTemplate>,
        key: String?,
        fieldElement: JSONObject,
        typeValidation: TypeValidation
    ): Boolean {
        val field = fieldArray.first { it.fieldName == key }
        val isValid: Boolean
        val value = fieldElement.get(key) as String
        if (isFieldIsNull(value)) {
            return false
        }

        isValid = valueTypeMap[field.type]!!.validateValueType(value, typeValidation)

        if (!isValid) {
            return true
        }
        return false
    }

    fun dependencyValidation(dataInJSONArray: JSONArray): JSONArray {
        val rowMap = JSONArray()
        val dependencyValidation = DependencyValidation()
        val fieldArray = metaDataReaderWriter.readFields()
        val errorMessage = "Dependency Error in "
        try {
            dataInJSONArray.forEachIndexed { index, element ->
                val fieldElement = (element as JSONObject)
                val keys = fieldElement.keySet()
                for (key in keys) {
                    if (depVal(fieldArray, key, fieldElement, dependencyValidation)) {
                        val jsonObject = JSONObject().put(
                            (index + 1).toString(),
                            "$errorMessage$key"
                        )
                        rowMap.put(jsonObject)
                    }
                }
            }
        } catch (err: Exception) {
            println(err.message)
            return JSONArray()
        }
        return rowMap
    }

    private fun depVal(
        fieldArray: Array<JsonMetaDataTemplate>,
        key: String?,
        fieldElement: JSONObject,
        dependencyValidation: DependencyValidation
    ): Boolean {
        val field = fieldArray.first { it.fieldName == key }
        val value = fieldElement.get(key) as String
        if (field.dependencies != null) {
            val dependencies = field.dependencies
            for (dependency in dependencies) {
                val dependentValue = fieldElement.get(dependency.dependentOn) as String
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