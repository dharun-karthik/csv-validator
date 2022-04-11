package response

enum class ContentType(
    val value: String
) {
    JSON("application/json"),
    HTML("text/html")
}