package validation.jsonConfig.mandatoryFields.dependency

import metaData.template.DependencyTemplate

class ExpectedDependentFieldValueValidator : DependencyValidator() {
    override fun validate(dependencyField: DependencyTemplate): String? {
        return checkDependencyField("expectedDependentFieldValue", dependencyField.expectedDependentFieldValue)
    }

}