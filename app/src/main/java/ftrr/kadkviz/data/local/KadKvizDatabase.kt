package ftrr.kadkviz.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [KvizEntity::class, EkipaEntity::class], version = 1, exportSchema = false)
abstract class KadKvizDatabase : RoomDatabase() {
    abstract val kvizDao: KvizDao
    abstract val ekipaDao: EkipaDao

    companion object {
        fun getInstance(context: Context): KadKvizDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                KadKvizDatabase::class.java,
                "kviz_database"
            ).build()
        }
    }
}