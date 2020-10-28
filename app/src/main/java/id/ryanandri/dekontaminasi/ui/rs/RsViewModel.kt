package id.ryanandri.dekontaminasi.ui.rs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Rumah Sakit"
    }
    val text: LiveData<String> = _text
}