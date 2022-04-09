package metaData

data class JsonMetaDataTemplate(
    val fieldName: String,
    val type: String,
    val length: String? = null,
    val minLength: String? = null,
    val maxLength: String? = null,
    val dependentOn: String? = null,
    val expectedDependentFieldValue: String? = null,
    val expectedCurrentFieldValue: String? = null,
    val values: List<String>? = null,
)