package metaData.template

data class DependencyTemplate(
    val dependentOn: String = "<empty>",
    val expectedDependentFieldValue: String = "<empty>",
    val expectedCurrentFieldValue: String = "<empty>",
)