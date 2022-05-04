package validation.jsonConfig

import org.json.JSONArray
import org.json.JSONObject

class JSONKeyValidator {
    fun validateKey(jsonField: JSONObject): List<String> {
        val validKeyList = listOf(
            "fieldName",
            "type",
            "isNullAllowed",
            "pattern",
            "length",
            "minLength",
            "maxLength",
            "dependencies",
            "values"
        )

        val keyErrorList = mutableListOf<String>()
        val keys = jsonField.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            if (key !in validKeyList) {
                keyErrorList.add("$key is not a valid key")
            }
            if (key == "dependencies") {
                val dependencyJSON = jsonField[key] as JSONArray
                dependencyJSON.forEach { jsonField ->
                    keyErrorList.addAll(validateDependencyKeys(jsonField as JSONObject))
                }
            }
        }
        return keyErrorList
    }

    private fun validateDependencyKeys(jsonField: JSONObject): List<String> {
        val validDependencyKeyList = listOf(
            "dependentOn",
            "expectedDependentFieldValue",
            "expectedCurrentFieldValue"
        )
        val dependencyKeyErrorList = mutableListOf<String>()
        val keys = jsonField.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            if (key !in validDependencyKeyList)
                dependencyKeyErrorList.add("$key is not a valid key inside dependency")
        }
        return dependencyKeyErrorList
    }
}