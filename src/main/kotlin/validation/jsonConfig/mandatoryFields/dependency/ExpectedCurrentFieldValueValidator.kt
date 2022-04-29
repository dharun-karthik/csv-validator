package validation.jsonConfig.mandatoryFields.dependency

import metaData.template.DependencyTemplate

class ExpectedCurrentFieldValueValidator : DependencyValidator() {
    override fun validate(dependencyField: DependencyTemplate): String? {
        return checkDependencyField("expectedCurrentFieldValue", dependencyField.expectedCurrentFieldValue)
    }
}