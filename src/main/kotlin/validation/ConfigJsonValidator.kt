package validation

import com.google.gson.Gson
import metaData.template.JsonConfigTemplate
import org.json.JSONArray
import org.json.JSONObject
import validation.jsonConfig.DateTimePatternValidator
import validation.jsonConfig.MandatoryFieldsValidator
import validation.jsonConfig.MaxMinValidator
import validation.jsonConfig.PositiveLengthValidator

class ConfigJsonValidator {
    private val configJsonValidators = listOf(
        MandatoryFieldsValidator(),
        DateTimePatternValidator(),
        PositiveLengthValidator(),
        MaxMinValidator(),
    )

    fun validate(content: String): JSONArray {
        val gson = Gson()
        val configFields = gson.fromJson(content, Array<JsonConfigTemplate>::class.java)

        val allErrorsInJson = JSONArray()
        configFields.forEachIndexed { index, jsonField ->
            val allErrorsForCurrentField = mutableListOf<String>()
            for (configValidator in configJsonValidators) {
                val errors = configValidator.validate(jsonField)
                allErrorsForCurrentField.addAll(errors)
            }
            val jsonError = generateJsonError(index, allErrorsForCurrentField)
            allErrorsInJson.put(jsonError)
        }
        return allErrorsInJson
    }

    private fun generateJsonError(index: Int, allErrorsForCurrentField: MutableList<String>): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put((index + 1).toString(), allErrorsForCurrentField)
        return jsonObject
    }
}