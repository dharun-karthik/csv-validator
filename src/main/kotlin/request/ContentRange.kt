package request

data class ContentRange(
    val unit: String,
    val rangeStart: Int,
    val rangeEnd: Int,
    val size: Int
)