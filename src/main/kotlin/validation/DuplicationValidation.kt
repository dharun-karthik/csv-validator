package validation

import org.json.JSONArray

class DuplicationValidation {
    fun getDuplicateRowNumberInJSON(dataInJSONArray: JSONArray): List<List<Int>> {
        val mapOfIndexOfDuplicateObjects: MutableMap<String, MutableList<Int>> = mutableMapOf()
        dataInJSONArray.forEachIndexed { index, element ->
            mapOfIndexOfDuplicateObjects.putIfAbsent(element.toString(), mutableListOf())
            mapOfIndexOfDuplicateObjects[element.toString()]!!.add(index + 1)
        }
        val mapOfIndexOfDuplicateObjectsWithOnlyDuplicateValues =
            mapOfIndexOfDuplicateObjects.filterValues { it.size > 1 }
        return mapOfIndexOfDuplicateObjectsWithOnlyDuplicateValues.values.toList()
    }
}