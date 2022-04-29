package metaData.template

data class JsonConfigTemplate(
    val fieldName: String = "<empty>",
    val type: String = "<empty>",
    val isNullAllowed: String? = null,
    val pattern: String? = null,
    val length: String? = null,
    val minLength: String? = null,
    val maxLength: String? = null,
    val dependencies: List<DependencyTemplate>? = null,
    val values: List<String>? = null,
)