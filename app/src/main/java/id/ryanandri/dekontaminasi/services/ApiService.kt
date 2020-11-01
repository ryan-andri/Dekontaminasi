package id.ryanandri.dekontaminasi.services

import id.ryanandri.dekontaminasi.ui.stats.StatsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("id/covid19/stats")
    suspend fun getStatsResponse() : Response<StatsResponse>

}