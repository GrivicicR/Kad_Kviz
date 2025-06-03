package ftrr.kadkviz.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EkipaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEkipa(ekipa: EkipaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEkipa(ekipe: List<EkipaEntity>)

    @Query("SELECT * FROM EkipaEntity")
    suspend fun getEkipeZaKviz(): List<EkipaEntity>

    @Delete
    suspend fun deleteEkipa(ekipa: EkipaEntity)

}