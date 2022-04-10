package validation

import org.json.JSONArray

class ColumnValidation {

    fun isValid(configJSON: String, jsonData: String): Boolean {
        val configJSONArray = JSONArray(configJSON)
        val jsonDataArray = JSONArray(jsonData)
        val fieldsInJsonData = getAllFieldNames(jsonDataArray)
        val noOfConfig = configJSONArray.length()
        for (index in 0 until noOfConfig) {
            val jsonObject = configJSONArray.getJSONObject(index)
            if (!fieldsInJsonData.contains(jsonObject.get("fieldName").toString())) {
                return false
            }
        }
        return true

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
        print(fieldNames)
        return fieldNames
    }

}
