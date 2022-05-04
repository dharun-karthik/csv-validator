package validation.jsonConfig

import metaData.template.DependencyTemplate
import org.json.JSONArray
import org.json.JSONObject
import validation.jsonConfig.mandatoryFields.dependency.DependentOnValidator
import validation.jsonConfig.mandatoryFields.dependency.ExpectedCurrentFieldValueValidator
import validation.jsonConfig.mandatoryFields.dependency.ExpectedDependentFieldValueValidator

class DependencyFieldsValidator {
    private val dependencyValidators = listOf(
        DependentOnValidator(),
        ExpectedDependentFieldValueValidator(),
        ExpectedCurrentFieldValueValidator(),
    )

    fun validate(dependencyFields: List<DependencyTemplate>?): JSONArray {
        val allErrorsInJson = JSONArray()
        if (dependencyFields == null) {
            return allErrorsInJson
        }
        for ((index, dependencyField) in dependencyFields.withIndex()) {
            val currentDependencyErrors = mutableListOf<String>()
            for (dependencyValidator in dependencyValidators) {
                val allErrorsForCurrentField = dependencyValidator.validate(dependencyField)
                if (allErrorsForCurrentField != null) {
                    currentDependencyErrors.add(allErrorsForCurrentField)
                }
            }
            if (currentDependencyErrors.isNotEmpty()) {
                val jsonError = generateJsonError(index, currentDependencyErrors)
                allErrorsInJson.put(jsonError)
            }
        }
        return allErrorsInJson
    }

    fun validateDependentOnColumnName(
        dependencies: List<DependencyTemplate>,
        fieldNameList: List<String>
    ): List<String> {
        val dependentOnColumnNameError = mutableListOf<String>()
        dependencies.forEach { dependency ->
            if (dependency.dependentOn !in fieldNameList) {
                dependentOnColumnNameError.add("Field name ${dependency.dependentOn} not found")
            }
        }
        return dependentOnColumnNameError
    }

    private fun generateJsonError(index: Int, allErrorsForCurrentField: MutableList<String>): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put((index + 1).toString(), allErrorsForCurrentField)
        return jsonObject
    }
}