package validation.jsonConfig

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
        }
        return keyErrorList
    }
}