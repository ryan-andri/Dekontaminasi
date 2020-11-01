package id.ryanandri.dekontaminasi.custom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.ryanandri.dekontaminasi.services.ApiService
import id.ryanandri.dekontaminasi.services.Repository
import id.ryanandri.dekontaminasi.ui.stats.StatsViewModel

class VmFactoryHandler(
        private val apiService: ApiService,
        private val repo : String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when (repo) {
            "stats" -> {
                if (modelClass.isAssignableFrom(StatsViewModel::class.java)) {
                    return StatsViewModel(Repository(apiService)) as T
                }
            }
        }
        throw IllegalArgumentException("Unknown repository")
    }
}