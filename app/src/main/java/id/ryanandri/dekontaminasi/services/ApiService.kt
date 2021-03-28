package id.ryanandri.dekontaminasi.services

import id.ryanandri.dekontaminasi.ui.news.NewsListResponse
import id.ryanandri.dekontaminasi.ui.rs.RsListResponse
import id.ryanandri.dekontaminasi.ui.stats.StatsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("id/covid19/stats")
    suspend fun getStatsResponse() : Response<StatsResponse>

    @GET("id/covid19/hospitals")
    suspend fun getRsList() : List<RsListResponse>

    @GET("id/covid19/news")
    suspend fun getNewsList() : List<NewsListResponse>
}