package id.ryanandri.dekontaminasi.ui.stats

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
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
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setup statsRecyclerView
        statsRv.setHasFixedSize(true)
        statsRv.layoutManager = LinearLayoutManager(view.context)
        statsAdapter = StatsRecyclerView(view.context, regionList)
        statsRv.adapter = statsAdapter

        statsRefresh.setOnRefreshListener {
            observeResponse(view, true)
        }

        // observe and show to the UI
        observeResponse(view, false)
    }

    private fun observeResponse(view: View, refresh : Boolean) {
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
                        if (refresh) {
                            statsRefresh.isRefreshing = false
                        } else {
                            loadingBar.visibility = View.GONE
                        }
                    }
                    Status.ERROR -> {
                        if (refresh) {
                            statsRefresh.isRefreshing = false
                            statsRv.visibility = View.VISIBLE
                        } else {
                            statsRv.visibility = View.VISIBLE
                            loadingBar.visibility = View.GONE
                        }
                        Toast.makeText(view.context, "Koneksi bermasalah!", Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        if (refresh) {
                            statsRv.visibility = View.GONE
                            regionList.clear()
                            statsAdapter.notifyDataSetChanged()
                        } else {
                            loadingBar.visibility = View.VISIBLE
                            statsRv.visibility = View.GONE
                        }
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search_tool, menu)

        val search : MenuItem = menu.findItem(R.id.search)
        val searchView : SearchView = search.actionView as SearchView
        searchView.queryHint = "Nama Provinsi"
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        if (p0 != null) {
                            statsAdapter.filter.filter(p0)
                            statsAdapter.notifyDataSetChanged()
                            return true
                        }
                        return false
                    }
                }
        )
    }
}