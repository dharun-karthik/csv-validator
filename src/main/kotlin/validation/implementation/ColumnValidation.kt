package validation.implementation

import org.json.JSONArray
import org.json.JSONObject

//todo validate with list of column names
class ColumnValidation {

    fun validate(metaDataJson: String, listOfHeaders: List<String>): JSONArray {
        val metaDataJsonArray = JSONArray(metaDataJson)
        val unavailableColumnJsonArray = JSONArray()
//        val fieldsInJsonData = getAllFieldNamesOfJsonData(listOfHeaders)
//        val fieldsInMetaData = getAllFieldNamesOfMetaData(metaDataJsonArray)
//        val unavailableFieldNames = fieldsInJsonData.filter { fieldName ->
//            !fieldsInMetaData.contains(fieldName)
//        }
//        if (unavailableFieldNames.isNotEmpty()) {
//            val obj = JSONObject()
//            obj.put("0", unavailableFieldNames)
//            unavailableColumnJsonArray.put(obj)
//        }
        return unavailableColumnJsonArray
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
