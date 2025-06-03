package ftrr.kadkviz.data.repository

import android.content.Context
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import ftrr.kadkviz.RetrofitClient
import ftrr.kadkviz.data.local.EkipaEntity
import ftrr.kadkviz.data.local.KadKvizDatabase
import ftrr.kadkviz.data.local.KvizEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class KadKvizRepository(context: Context) {
    private val kvizDao = KadKvizDatabase.getInstance(context).kvizDao
    private val ekipaDao = KadKvizDatabase.getInstance(context).ekipaDao
    private val kvizService = RetrofitClient.getKvizClient()
    private val firestore = Firebase.firestore

    suspend fun insertKviz(kviz: KvizEntity): Boolean {
        return try {
            val reference = firestore.collection("kvizovi")
                .document()

            val documentId = reference.id
            val kvizWithId = kviz.copy(id = documentId)
            reference.set(kvizWithId)
                .await()

            Log.d("Kad kviz repository", "Uspješno dodan kviz")

            withContext(Dispatchers.IO) {
                kvizDao.insertKviz(kvizWithId)
            }
            true
        } catch (e: Exception) {
            Log.e("Kad kviz repository", "Greška pri dodavanju kviza", e)
            false
        }
    }

    suspend fun getAllKviz(): List<KvizEntity> =
        suspendCancellableCoroutine { continuation ->
            firestore.collection("kvizovi")
                .get()
                .addOnSuccessListener { result ->
                    val kvizListFromFirestore = result.toObjects(KvizEntity::class.java)
                    Log.d(
                        "KadKvizRepository",
                        "Uspješno dohvaćeni kvizovi iz Firestore: ${kvizListFromFirestore.size} items"
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            kvizDao.insertKviz(kvizListFromFirestore)
                            val localKvizList = kvizDao.getAllKviz()
                            if (continuation.isActive) {
                                continuation.resume(localKvizList)
                            }
                        } catch (e: Exception) {
                            Log.e("KadKvizRepository", "Greška pri dohvaćanju lokalnih kviza", e)
                            if (continuation.isActive) {
                                continuation.resume(emptyList())
                            }
                        }
                    }

                }
                .addOnFailureListener { exception ->
                    Log.e(
                        "KadKvizRepository",
                        "Greška pri dohvaćanju kviza iz Firestore",
                        exception
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val localKvizList = kvizDao.getAllKviz()
                            if (continuation.isActive) {
                                continuation.resume(localKvizList)
                            }
                        } catch (dbException: Exception) {
                            Log.e(
                                "KadKvizRepository",
                                "Greška pri dohvaćanju iz lokalne baze nakon Firestore neuspjeha.",
                                dbException
                            )
                            if (continuation.isActive) {
                                continuation.resumeWithException(dbException)
                            }
                        }
                    }
                }

            continuation.invokeOnCancellation {
                Log.d("KadKvizRepository", "Dohvaćanje kviza otkazano.")
            }
        }

    suspend fun deleteAllKviz() {
        return kvizDao.deleteAllKviz()
    }

    suspend fun getAllTeams(): List<EkipaEntity> =
        suspendCancellableCoroutine { continuation ->
            firestore.collection("ekipe")
                .get()
                .addOnSuccessListener { result ->
                    val teamsListFromFirestore = result.toObjects(EkipaEntity::class.java)
                    Log.d(
                        "KadKvizRepository",
                        "Uspješno dohvaćene ekipe iz Firestore: ${teamsListFromFirestore.size} items"
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            ekipaDao.insertEkipa(teamsListFromFirestore)
                            val localEkipaList = ekipaDao.getEkipeZaKviz()
                            if (continuation.isActive) {
                                continuation.resume(localEkipaList)
                            }
                        } catch (e: Exception) {
                            Log.e("KadKvizRepository", "Greška pri dohvaćanju lokalnih ekipa", e)
                            if (continuation.isActive) {
                                continuation.resume(emptyList())
                            }
                        }
                    }

                }
                .addOnFailureListener { exception ->
                    Log.e(
                        "KadKvizRepository",
                        "Greška pri dohvaćanju ekipa iz Firestore",
                        exception
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val localEkipaList = ekipaDao.getEkipeZaKviz()
                            if (continuation.isActive) {
                                continuation.resume(localEkipaList)
                            }
                        } catch (dbException: Exception) {
                            Log.e(
                                "KadKvizRepository",
                                "Greška pri dohvaćanju iz lokalne baze nakon Firestore neuspjeha.",
                                dbException
                            )
                            if (continuation.isActive) {
                                continuation.resumeWithException(dbException)
                            }
                        }
                    }
                }

            continuation.invokeOnCancellation {
                Log.d("KadKvizRepository", "Dohvaćanje kviza otkazano.")
            }
        }

    suspend fun addTeamToQuiz(ekipa: EkipaEntity): Boolean {
        return try {
            firestore.collection("ekipe")
                .add(ekipa)
                .await()

            Log.d("Kad kviz repository", "Uspješno dodana ekipa")

            withContext(Dispatchers.IO) {
                ekipaDao.insertEkipa(ekipa)
            }
            true
        } catch (e: Exception) {
            Log.e("Kad kviz repository", "Greška pri dodavanju ekipe", e)
            false
        }
    }
}
