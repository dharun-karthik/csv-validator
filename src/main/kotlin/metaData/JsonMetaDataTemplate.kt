package metaData

data class JsonMetaDataTemplate(
    val fieldName: String,
    val type: String,
    val length: Int? = null,
    val minLength: Int? = null,
    val maxLength: Int? = null,
    val dependentOn: String? = null,
    val expectedDependentFieldValue: String? = null,
    val expectedCurrentFieldValue: String? = null,
    val values: List<String>? = null,
)