package validation.jsonConfig.mandatoryFields.dependency

import metaData.template.DependencyTemplate

class DependentOnValidator : DependencyValidator() {
    override fun validate(dependencyField: DependencyTemplate): String? {
        return checkDependencyField("dependentOn", dependencyField.dependentOn)
    }
}