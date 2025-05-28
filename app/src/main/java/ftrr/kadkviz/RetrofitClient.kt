package ftrr.kadkviz

import ftrr.kadkviz.data.remote.KadKVizService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitClient {
    private fun provideInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private fun provideRetrofitClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(provideInterceptor())
            .build()
    }

    fun getKvizClient(): KadKVizService {
        return Retrofit.Builder()
            .baseUrl(KadKVizService.BASE_URL)
            .client(provideRetrofitClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}