package validation.jsonConfig.mandatoryFields.dependency

import metaData.template.DependencyTemplate

abstract class DependencyValidator {

    abstract fun validate(dependencyField: DependencyTemplate): String?

    protected fun checkDependencyField(fieldName: String, fieldValue: String): String? {
        if (fieldValue == "<empty>") {
            return "Dependency field '$fieldName' should be present"
        }
        return null
    }
}