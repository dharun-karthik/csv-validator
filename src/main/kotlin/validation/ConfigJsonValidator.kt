package validation

import com.google.gson.Gson
import metaData.template.JsonConfigTemplate
import org.json.JSONArray
import org.json.JSONObject
import validation.jsonConfig.*


class ConfigJsonValidator {
    private val configJsonValidators = listOf(
        MandatoryFieldsValidator(),
        SupportedFieldTypeValidator(),
        DateTimePatternValidator(),
        SupportedPatternTypeValidator(),
        PositiveLengthValidator(),
        MaxMinValidator(),
    )

    fun validate(content: String): JSONArray {
        val allErrorsInJsonArray = JSONArray()
        val gson = Gson()
        val configFields = gson.fromJson(content, Array<JsonConfigTemplate>::class.java)
        val dependencyFieldsValidator = DependencyFieldsValidator()
        val jsonKeyValidator = JSONKeyValidator()
        val jsonFieldArray = JSONArray(content)

        configFields.forEachIndexed { index, jsonField ->
            val allErrorsForCurrentField = mutableListOf<String>()
            println(jsonFieldArray[index] as JSONObject)
            val keyErrors = jsonKeyValidator.validateKey(jsonFieldArray[index] as JSONObject)
            allErrorsForCurrentField.addAll(keyErrors)
            for (configValidator in configJsonValidators) {
                val errors = configValidator.validate(jsonField)
                allErrorsForCurrentField.addAll(errors)
            }
            val allErrors = JSONArray()
            val dependencyErrors = dependencyFieldsValidator.validate(jsonField.dependencies)
            if (!dependencyErrors.isEmpty) {
                val dependencyErrorInJson = generateJsonError("Dependency errors", dependencyErrors)
                allErrors.put(dependencyErrorInJson)
            }
            if (allErrorsForCurrentField.isNotEmpty()) {
                val jsonError = generateJsonError("Field errors", allErrorsForCurrentField)
                allErrors.put(jsonError)
            }
            if (!allErrors.isEmpty) {
                allErrorsInJsonArray.put(generateJsonError((index + 1).toString(), allErrors))
            }
        }

        return allErrorsInJsonArray
    }

    private fun generateJsonError(message: String, content: Any): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put(message, content)
        return jsonObject
    }
}