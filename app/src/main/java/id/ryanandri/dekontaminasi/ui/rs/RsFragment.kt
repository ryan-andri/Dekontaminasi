package id.ryanandri.dekontaminasi.ui.rs

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.ryanandri.dekontaminasi.R
import id.ryanandri.dekontaminasi.custom.VmFactoryHandler
import id.ryanandri.dekontaminasi.services.RetrofitService
import id.ryanandri.dekontaminasi.services.Status
import kotlinx.android.synthetic.main.fragment_rs.*
import kotlinx.android.synthetic.main.loading.*

class RsFragment : Fragment() {

    private lateinit var rsViewModel: RsViewModel

    private lateinit var rsAdapter : RsRecyclerView
    private var rsList : MutableList<RsListResponse> = ArrayList()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        rsViewModel = ViewModelProvider(this,
                VmFactoryHandler(RetrofitService.apiService, "rs"))
                .get(RsViewModel::class.java)
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_rs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rsRv.setHasFixedSize(true)
        rsRv.layoutManager = LinearLayoutManager(view.context)
        rsAdapter = RsRecyclerView(view.context, rsList)
        rsRv.adapter = rsAdapter

        observeData(view, false)

        rsRefresh.setOnRefreshListener {
            observeData(view, true)
        }
    }

    private fun observeData(view: View, refresh : Boolean) {
        rsViewModel.getRsData().observe(viewLifecycleOwner, {
            it?.let {
                resources ->
                when (resources.status) {
                    Status.SUCCESS -> {
                        rsList.addAll(resources.data as List<RsListResponse>)
                        rsAdapter.notifyDataSetChanged()
                        rsRv.visibility = View.VISIBLE
                        if (refresh) {
                            rsRefresh.isRefreshing = false
                        } else {
                            loadingBar.visibility = View.GONE
                        }
                    }
                    Status.ERROR -> {
                        if (refresh) {
                            rsRefresh.isRefreshing = false
                            rsRv.visibility = View.VISIBLE
                        } else {
                            rsRv.visibility = View.VISIBLE
                            loadingBar.visibility = View.GONE
                        }
                        Toast.makeText(view.context, "Koneksi bermasalah!", Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        if (refresh) {
                            rsRv.visibility = View.GONE
                            rsList.clear()
                            rsAdapter.notifyDataSetChanged()
                        } else {
                            loadingBar.visibility = View.VISIBLE
                            rsRv.visibility = View.GONE
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
        searchView.queryHint = "Nama RS atau Provinsi"
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        if (p0 != null) {
                            rsAdapter.filter.filter(p0)
                            rsAdapter.notifyDataSetChanged()
                            return true
                        }
                        return false
                    }
                }
        )
    }
}