package id.ryanandri.dekontaminasi.ui.stats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StatsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Statistik Fragment"
    }
    val text: LiveData<String> = _text
}