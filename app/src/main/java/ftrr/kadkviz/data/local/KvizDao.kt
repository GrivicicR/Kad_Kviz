package ftrr.kadkviz.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface KvizDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKviz(kviz: List<KvizEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKviz(kviz: KvizEntity)

    @Query("SELECT * FROM kvizentity")
    suspend fun getAllKviz(): List<KvizEntity>

    @Query("DELETE FROM kvizentity")
    suspend fun deleteAllKviz()
}