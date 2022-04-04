package validation

import org.json.JSONArray

class DuplicationValidation {
    fun getDuplicateRowNumberInJSON(dataInJSONArray: JSONArray): List<List<Int>> {
        val mapOfObjectsAndIndices: MutableMap<String, MutableList<Int>> = mutableMapOf()
        dataInJSONArray.forEachIndexed { index, element ->
            mapOfObjectsAndIndices.putIfAbsent(element.toString(), mutableListOf())
            mapOfObjectsAndIndices.getOrDefault(element.toString(), mutableListOf()).add(index + 1)
        }
        val mapOfIndexOfDuplicateObjectsWithOnlyDuplicateValues =
            mapOfObjectsAndIndices.filterValues { it.size > 1 }
        return mapOfIndexOfDuplicateObjectsWithOnlyDuplicateValues.values.toList()
    }
}