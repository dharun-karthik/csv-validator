package metaData.template

data class JsonMetaDataTemplate(
    val fieldName: String,
    val type: String,
    val isNullAllowed: String? = null,
    val pattern: String? = null,
    val length: String? = null,
    val minLength: String? = null,
    val maxLength: String? = null,
    val dependencies: List<DependencyTemplate>? = null,
    val values: List<String>? = null,
)