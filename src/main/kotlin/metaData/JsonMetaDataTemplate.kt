package metaData

data class JsonMetaDataTemplate(
    val fieldName: String?,
    val type: String?,
    val length: Int?,
    val minLength: Int?,
    val maxLength: Int?,
    val values: List<String>?,
)