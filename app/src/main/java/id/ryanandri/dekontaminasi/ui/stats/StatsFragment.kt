package id.ryanandri.dekontaminasi.ui.stats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import id.ryanandri.dekontaminasi.R
import id.ryanandri.dekontaminasi.custom.VmFactoryHandler
import id.ryanandri.dekontaminasi.services.RetrofitService
import id.ryanandri.dekontaminasi.services.Status

class StatsFragment : Fragment() {

    private lateinit var statsViewModel: StatsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        statsViewModel = ViewModelProvider(this,
                        VmFactoryHandler(RetrofitService.apiService, "stats"))
                        .get(StatsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeResponse()
    }

    private fun observeResponse() {
        statsViewModel.getResponse().observe(viewLifecycleOwner, {
            it?.let {
                resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Log.d("Success", resource.data?.body().toString())
                    }
                    Status.ERROR -> {
                        Log.d("Failed", "Something error!")
                    }
                    Status.LOADING -> {
                        Log.d("Loading", "Fetching Data!")
                    }
                }
            }
        })
    }
}