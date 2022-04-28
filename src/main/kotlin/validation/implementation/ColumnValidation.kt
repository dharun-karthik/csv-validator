package validation.implementation

import org.json.JSONArray
import org.json.JSONObject

class ColumnValidation {

    fun validate(metaDataJson: String, fieldsInCsv: List<String>): JSONArray {
        val metaDataJsonArray = JSONArray(metaDataJson)
        val unavailableColumnJsonArray = JSONArray()
        val fieldsInMetaData = getAllFieldNamesOfMetaData(metaDataJsonArray)
        val unavailableFieldNames = fieldsInCsv.filter { fieldName ->
            !fieldsInMetaData.contains(fieldName.lowercase())
        }
        if (unavailableFieldNames.isNotEmpty()) {
            val obj = JSONObject()
            obj.put("0", unavailableFieldNames)
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
