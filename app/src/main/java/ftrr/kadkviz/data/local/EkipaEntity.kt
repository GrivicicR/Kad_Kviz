package ftrr.kadkviz.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EkipaEntity(
    @PrimaryKey
    val ime: String = "",
    val kvizId: String = ""
)