package validation.operation

import metaData.template.DependencyTemplate
import metaData.template.JsonConfigTemplate
import org.json.JSONObject
import validation.implementation.DependencyValidation

class DependencyValidationOperation : ValidationOperation {
    override fun validate(
        metaDataField: JsonConfigTemplate,
        currentFieldValue: String,
        key: String,
        currentRow: JSONObject?
    ): String? {
        val dependencyValidation = DependencyValidation()
        if (metaDataField.dependencies != null) {
            val dependencies = metaDataField.dependencies
            return checkDependency(
                dependencies, currentRow!!, dependencyValidation, currentFieldValue, key
            )
        }
        return null
    }

    private fun checkDependency(
        dependencies: List<DependencyTemplate>,
        currentRow: JSONObject,
        dependencyValidation: DependencyValidation,
        currentFieldValue: String,
        key: String
    ): String? {
        for (dependency in dependencies) {
            val dependentValue = currentRow.getString(dependency.dependentOn)
            val expectedCurrentValue = dependency.expectedCurrentFieldValue
            val expectedDependentValue = dependency.expectedDependentFieldValue

            val isValid = dependencyValidation.validate(
                currentFieldValue, dependentValue, expectedDependentValue, expectedCurrentValue
            )

            if (!isValid) {
                return "${dependency.dependentOn} is $dependentValue but $key is $currentFieldValue"
            }
        }
        return null
    }

}