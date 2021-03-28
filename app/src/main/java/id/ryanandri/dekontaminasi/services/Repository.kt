package id.ryanandri.dekontaminasi.services

class Repository(private val apiService: ApiService) {
    suspend fun getStatsResponse() = apiService.getStatsResponse()
    suspend fun getListRs() = apiService.getRsList()
    suspend fun getListNews() = apiService.getNewsList()
}