package validation

import org.json.JSONArray
import org.json.JSONObject

class DuplicationValidation {
    fun getDuplicateRowNumberInJSON(dataInJSONArray: JSONArray): JSONArray {
        val mapOfObjectsAndIndices: MutableMap<String, Int> = mutableMapOf()
        val jsonArrayOfDuplicateElements = JSONArray()
        dataInJSONArray.forEachIndexed { index, element ->
            addElementToMap(mapOfObjectsAndIndices, element, index, jsonArrayOfDuplicateElements)
        }
        return jsonArrayOfDuplicateElements
    }

    private fun addElementToMap(
        mapOfObjectsAndIndices: MutableMap<String, Int>,
        element: Any,
        index: Int,
        jsonArrayOfDuplicateElements: JSONArray
    ) {
        if (mapOfObjectsAndIndices[element.toString()] == null) {
            mapOfObjectsAndIndices[element.toString()] = index + 1
            return
        }

        val jsonObject = JSONObject().put(
            getLineMessageWithKey(index + 1),
            "Row Duplicated From ${mapOfObjectsAndIndices[element.toString()]}"
        )
        jsonArrayOfDuplicateElements.put(jsonObject)
    }
    private fun getLineMessageWithKey(index: Int): String{
        return "Line Number $index"
    }
}