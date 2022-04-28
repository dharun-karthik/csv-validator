package validation

import com.google.gson.Gson
import metaData.ConfigFileReaderWriter
import metaData.template.JsonConfigTemplate
import org.json.JSONArray
import org.json.JSONObject
import validation.jsonConfig.DateTimePatternValidator

//todo refactor
class ConfigJsonValidator(path: String, val content: String? = null) {
    val configFileReaderWriter = ConfigFileReaderWriter(path)

    fun validate(): JSONArray {
        val gson = Gson()
        val configFields = gson.fromJson(content, Array<JsonConfigTemplate>::class.java)
        val allErrors = JSONArray()
        for (jsonField in configFields) {
            val error = DateTimePatternValidator().validate(jsonField)
            if (error.isNotEmpty()) {
                val datePatternError = JSONObject()
                datePatternError.put(jsonField.fieldName, error)
                allErrors.put(datePatternError)
            }
        }
        return allErrors
    }
}