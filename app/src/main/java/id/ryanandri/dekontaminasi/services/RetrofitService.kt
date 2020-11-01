package id.ryanandri.dekontaminasi.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    private const val API_URL = "https://dekontaminasi.com/api/"
    private fun build(): Retrofit {
        val client : OkHttpClient = OkHttpClient.Builder().build()
        return Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val apiService: ApiService = build().create(ApiService::class.java)
}