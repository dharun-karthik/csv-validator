package metaData

data class JsonMetaDataTemplate(
    val fieldName: String,
    val type: String,
    val length: String? = null,
    val minLength: String? = null,
    val maxLength: String? = null,
    val dependencies: List<DependencyTemplate>? = null,
    val values: List<String>? = null,
)