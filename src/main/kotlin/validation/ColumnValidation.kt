package validation

import org.json.JSONArray
import org.json.JSONObject


class ColumnValidation {

    fun getInvalidFieldNames(metaDataJson: String, jsonDataArray: JSONArray): JSONArray {
        val metaDataJsonArray = JSONArray(metaDataJson)
        val errorJsonArray = JSONArray()
        val fieldsInJsonData = getAllFieldNames(jsonDataArray)
        val noOfConfig = metaDataJsonArray.length()
        for (index in 0 until noOfConfig) {
            val configJsonObject = metaDataJsonArray.getJSONObject(index)
            val configFieldName = configJsonObject.get("fieldName").toString()
            if (!fieldsInJsonData.contains(configFieldName)) {
                val obj = JSONObject()
                obj.put("Column Name Error", configFieldName)
                errorJsonArray.put(obj)
            }
        }
        return errorJsonArray

    }

    private fun getAllFieldNames(jsonDataArray: JSONArray): List<String> {
        val fieldNames = mutableListOf<String>()
        if (jsonDataArray.isEmpty) {
            return fieldNames
        }
        val jsonObjectKeyIterator = jsonDataArray.getJSONObject(0).keys()
        while (jsonObjectKeyIterator.hasNext()) {
            fieldNames.add(jsonObjectKeyIterator.next())
        }
        return fieldNames
    }

}
