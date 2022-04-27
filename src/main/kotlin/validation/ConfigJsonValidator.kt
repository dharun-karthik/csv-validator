package validation

import metaData.ConfigFileReaderWriter
import org.json.JSONArray
import org.json.JSONObject

class ConfigJsonValidator(path: String) {
    val configFileReaderWriter = ConfigFileReaderWriter(path)

    fun validate(): JSONArray {
        val configData = configFileReaderWriter.readFields()
        val invalidConfigsJsonArray = JSONArray()

        val fixedLengthInvalidConfigs = configData.filter { it.length != null && it.length.toInt() < 1 }
        val fixedLengthErrorJsonObject =
            createJsonObject("Fixed len less than one", fixedLengthInvalidConfigs.map { it.fieldName })
        invalidConfigsJsonArray.put(fixedLengthErrorJsonObject)

        val minLengthInvalidConfigs = configData.filter {
            it.minLength != null && it.minLength.toInt() < 1
        }
        val minLengthInvalidJsonObject =
            createJsonObject("Min length less than one", minLengthInvalidConfigs.map { it.fieldName })
        invalidConfigsJsonArray.put(minLengthInvalidJsonObject)

        val maxLengthInvalidConfigs = configData.filter {
            it.maxLength != null && it.maxLength.toInt() < 1
        }
        val maxLengthInvalidJsonObject =
            createJsonObject("Max length less than one", maxLengthInvalidConfigs.map { it.fieldName })
        invalidConfigsJsonArray.put(maxLengthInvalidJsonObject)

        val minMaxLengthInvalidConfigs = configData.filter {
            it.minLength != null && it.maxLength!= null && it.minLength.toInt() >= it.maxLength.toInt()
        }
        val minMaxLengthInvalidJsonObject =
            createJsonObject("Min length greater than max length", minMaxLengthInvalidConfigs.map { it.fieldName })
        invalidConfigsJsonArray.put(minMaxLengthInvalidJsonObject)


        return invalidConfigsJsonArray
    }

    private fun createJsonObject(key: String, value: List<String>): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put(key, value)
        return jsonObject
    }
}