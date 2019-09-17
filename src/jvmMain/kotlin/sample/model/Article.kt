package sample.model

data class Article(
    val author: String,
    val dateCreated: String,
    val title: String,
    val minRead: String,
    val body: String
) {
    var id: Int? = null
}