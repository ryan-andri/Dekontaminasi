package id.ryanandri.dekontaminasi.ui.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.ryanandri.dekontaminasi.R
import id.ryanandri.dekontaminasi.custom.VmFactoryHandler
import id.ryanandri.dekontaminasi.services.RetrofitService
import id.ryanandri.dekontaminasi.services.Status
import kotlinx.android.synthetic.main.fragment_stats.*
import kotlinx.android.synthetic.main.loading.*

class StatsFragment : Fragment() {

    private lateinit var statsViewModel: StatsViewModel

    private lateinit var statsAdapter : StatsRecyclerView
    private var regionList : MutableList<RegionsItem> = ArrayList()

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

        // setup statsRecyclerView
        statsRv.setHasFixedSize(true)
        statsRv.layoutManager = LinearLayoutManager(view.context)
        statsAdapter = StatsRecyclerView(view.context, regionList)
        statsRv.adapter = statsAdapter

        // observe and show to the UI
        observeResponse(view)
    }

    private fun observeResponse(view: View) {
        statsViewModel.getResponse().observe(viewLifecycleOwner, {
            it?.let {
                resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        val response = resource.data?.body()
                        val regions : List<RegionsItem?>? = response?.regions
                        regionList.addAll(regions as List<RegionsItem>)
                        statsAdapter.notifyDataSetChanged()
                        statsRv.visibility = View.VISIBLE
                        loadingBar.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        statsRv.visibility = View.VISIBLE
                        loadingBar.visibility = View.GONE
                        Toast.makeText(view.context, "Koneksi bermasalah!", Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        loadingBar.visibility = View.VISIBLE
                        statsRv.visibility = View.GONE
                    }
                }
            }
        })
    }
}