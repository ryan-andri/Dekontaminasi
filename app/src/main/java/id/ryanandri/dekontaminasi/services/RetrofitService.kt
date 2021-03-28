package id.ryanandri.dekontaminasi.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    private const val API_URL = "https://dekontaminasi.com/api/"
    private const val ABOUT_URL = "https://api.github.com/users/"

    private fun build(url : String): Retrofit {
        val client : OkHttpClient = OkHttpClient.Builder().build()
        return Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val apiService: ApiService = build(API_URL).create(ApiService::class.java)
    val aboutService: ApiService = build(ABOUT_URL).create(ApiService::class.java)
}