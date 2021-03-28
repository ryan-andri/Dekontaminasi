package id.ryanandri.dekontaminasi.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.ryanandri.dekontaminasi.services.Repository
import id.ryanandri.dekontaminasi.services.ResponseHandler
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class NewsViewModel(private val repository: Repository) : ViewModel() {
    fun getNewsData() = liveData(Dispatchers.IO) {
        emit(ResponseHandler.loading(data = null))
        try {
            emit(ResponseHandler.success(data = repository.getListNews()))
        } catch (e : Exception) {
            emit(ResponseHandler.error(data = null, message = e.message ?: "Error Exception!"))
        }
    }
}