package id.ryanandri.dekontaminasi.ui.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.ryanandri.dekontaminasi.services.Repository
import id.ryanandri.dekontaminasi.services.ResponseHandler
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class AboutViewModel(private val repository: Repository) : ViewModel() {
    fun getAboutData() = liveData(Dispatchers.IO) {
        emit(ResponseHandler.loading(data = null))
        try {
            emit(ResponseHandler.success(data = repository.getAbout()))
        } catch (e : Exception) {
            emit(ResponseHandler.error(data = null, message = e.message ?: "Error Exception!"))
        }
    }
}