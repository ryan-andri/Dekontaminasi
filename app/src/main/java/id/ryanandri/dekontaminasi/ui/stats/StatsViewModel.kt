package id.ryanandri.dekontaminasi.ui.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.ryanandri.dekontaminasi.services.Repository
import id.ryanandri.dekontaminasi.services.ResponseHandler
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class StatsViewModel(private val repository: Repository) : ViewModel() {
    fun getResponse() = liveData(Dispatchers.IO) {
        emit(ResponseHandler.loading(data = null))
        try {
            emit(ResponseHandler.success(data = repository.getStatsResponse()))
        } catch (e : Exception) {
            emit(ResponseHandler.error(data = null, message = e.message ?: "Error Exception!"))
        }
    }
}