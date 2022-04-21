package metaData.template

data class DependencyTemplate(
    val dependentOn: String,
    val expectedDependentFieldValue: String,
    val expectedCurrentFieldValue: String,
)