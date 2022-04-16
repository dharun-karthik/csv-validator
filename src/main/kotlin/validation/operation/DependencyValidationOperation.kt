package validation.operation

import metaData.DependencyTemplate
import metaData.JsonMetaDataTemplate
import org.json.JSONObject
import validation.implementation.DependencyValidation

class DependencyValidationOperation : ValidationOperation {
    override fun validate(
        metaDataField: JsonMetaDataTemplate,
        currentFieldValue: String,
        currentRow: JSONObject?
    ): Boolean {
        val dependencyValidation = DependencyValidation()
        if (metaDataField.dependencies != null) {
            val dependencies = metaDataField.dependencies
            if (checkDependency(dependencies, currentRow!!, dependencyValidation, currentFieldValue)) {
                return false
            }
        }
        return true
    }

    private fun checkDependency(
        dependencies: List<DependencyTemplate>,
        currentRow: JSONObject,
        dependencyValidation: DependencyValidation,
        currentFieldValue: String
    ): Boolean {
        for (dependency in dependencies) {
            val dependentValue = currentRow.getString(dependency.dependentOn)
            val expectedCurrentValue = dependency.expectedCurrentFieldValue
            val expectedDependentValue = dependency.expectedDependentFieldValue

            val isValid = dependencyValidation.validate(
                currentFieldValue, dependentValue, expectedDependentValue, expectedCurrentValue
            )

            if (!isValid) {
                return true
            }
        }
        return false
    }

}