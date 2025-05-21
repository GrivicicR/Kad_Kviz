package ftrr.kadkviz

data class Kviz(
    val name: String,
    val image: String? = null,
    val location: String,
    val date: String,
    val time: String
)
