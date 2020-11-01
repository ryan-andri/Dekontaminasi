package id.ryanandri.dekontaminasi.services

class Repository(private val apiService: ApiService) {
    suspend fun getStatsResponse() = apiService.getStatsResponse()
}