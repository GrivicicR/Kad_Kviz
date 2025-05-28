package ftrr.kadkviz.data.repository

import android.content.Context
import ftrr.kadkviz.RetrofitClient
import ftrr.kadkviz.data.local.KadKvizDatabase
import ftrr.kadkviz.data.local.KvizEntity

class KadKvizRepository(context: Context) {
    private val kvizDao = KadKvizDatabase.getInstance(context).kvizDao
    private val kvizService = RetrofitClient.getKvizClient()

    suspend fun insertKviz(kviz: KvizEntity) {
        kvizDao.insertKviz(kviz)
    }

    suspend fun getAllKviz(): List<KvizEntity> {
        return kvizDao.getAllKviz()
    }

    suspend fun deleteAllKviz() {
        return kvizDao.deleteAllKviz()
    }
}