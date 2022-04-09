package metaData

data class JsonMetaDataTemplate(
    val fieldName: String? = null,
    val type: String,
    val length: Int? = null,
    val minLength: Int? = null,
    val maxLength: Int? = null,
    val dependent: String? = null,
    val dependentCondition: String? = null,
    val currentValueWhenThatConditionIsMet: String? = null,
    val values: List<String>? = null,
)