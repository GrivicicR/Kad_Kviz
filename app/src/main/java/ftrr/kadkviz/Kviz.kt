package ftrr.kadkviz

data class Kviz(
    val name: String,
    val location: String,
    val date: String,
    val time: String,
    val entryFee: String = "nema opisa",
    val teamSize: String = "N/A",
    val description: String = "N/A"
)
