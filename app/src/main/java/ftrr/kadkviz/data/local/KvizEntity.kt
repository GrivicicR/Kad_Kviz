package ftrr.kadkviz.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class KvizEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val location: String,
    val date: String,
    val time: String,
    val entryFee: String = "nema opisa",
    val teamSize: String = "N/A",
    val description: String = "N/A"
)
