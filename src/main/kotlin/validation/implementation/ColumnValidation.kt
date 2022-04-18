package validation.implementation

import org.json.JSONArray
import org.json.JSONObject


class ColumnValidation {

    fun getInvalidFieldNames(metaDataJson: String, jsonDataArray: JSONArray): JSONArray {
        val metaDataJsonArray = JSONArray(metaDataJson)
        val errorJsonArray = JSONArray()
        val fieldsInJsonData = getAllFieldNamesOfJsonData(jsonDataArray)
        val noOfConfig = metaDataJsonArray.length()
        for (index in 0 until noOfConfig) {
            val configJsonObject = metaDataJsonArray.getJSONObject(index)
            val configFieldName = configJsonObject.get("fieldName").toString()
            if (!fieldsInJsonData.contains(configFieldName.lowercase())) {
                val obj = JSONObject()
                obj.put("Column Name Error", configFieldName)
                errorJsonArray.put(obj)
            }
        }
        return errorJsonArray

    }

    private fun getAllFieldNamesOfJsonData(jsonDataArray: JSONArray): List<String> {
        val fieldNames = mutableListOf<String>()
        if (jsonDataArray.isEmpty) {
            return fieldNames
        }
        val jsonObjectKeyIterator = jsonDataArray.getJSONObject(0).keys()
        while (jsonObjectKeyIterator.hasNext()) {
            fieldNames.add(jsonObjectKeyIterator.next().lowercase())
        }
        return fieldNames
    }

    fun getColumnsNotInConfig(metaDataJson: String, jsonDataArray: JSONArray): JSONArray {
        val metaDataJsonArray = JSONArray(metaDataJson)
        val unavailableColumnJsonArray = JSONArray()
        val fieldsInJsonData = getAllFieldNamesOfJsonData(jsonDataArray)
        val fieldsInMetaData = getAllFieldNamesOfMetaData(metaDataJsonArray)
        val unavailableFieldNames = fieldsInJsonData.filter { fieldName ->
            !fieldsInMetaData.contains(fieldName)
        }
        if (unavailableFieldNames.isNotEmpty()){
            val obj = JSONObject()
            obj.put("0",unavailableFieldNames)
            unavailableColumnJsonArray.put(obj)
        }
        return unavailableColumnJsonArray
    }

    private fun getAllFieldNamesOfMetaData(metaDataJsonArray: JSONArray): List<String> {
        val fieldNames = mutableListOf<String>()
        if (metaDataJsonArray.isEmpty) {
            return fieldNames
        }
        val noOfConfig = metaDataJsonArray.length()
        for (index in 0 until noOfConfig) {
            val configJsonObject = metaDataJsonArray.getJSONObject(index)
            val configFieldName = configJsonObject.get("fieldName").toString().lowercase()
            fieldNames.add(configFieldName)
        }
        return fieldNames
    }

}
