package id.ryanandri.dekontaminasi.ui.news

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import id.ryanandri.dekontaminasi.R
import id.ryanandri.dekontaminasi.custom.VmFactoryHandler
import id.ryanandri.dekontaminasi.services.RetrofitService
import id.ryanandri.dekontaminasi.services.Status
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.loading.*

class NewsFragment : Fragment() {

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsAdapter: NewsRecyclerView
    private var newsList : MutableList<NewsListResponse> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        newsViewModel = ViewModelProvider(this,
                VmFactoryHandler(RetrofitService.apiService, "news"))
                .get(NewsViewModel::class.java)
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsRv.setHasFixedSize(true)
        newsRv.layoutManager = LinearLayoutManager(view.context)
        newsAdapter = NewsRecyclerView(view.context, newsList)
        newsRv.adapter = newsAdapter

        observeData(view, false)

        newsRefresh.setOnRefreshListener {
            observeData(view, true)
        }
    }

    private fun observeData(view: View, refresh : Boolean) {
        newsViewModel.getNewsData().observe(viewLifecycleOwner, {
            it?.let {
                resources ->
                when (resources.status) {
                    Status.SUCCESS -> {
                        newsList.addAll(resources.data as List<NewsListResponse>)
                        newsAdapter.notifyDataSetChanged()
                        newsRv.visibility = View.VISIBLE
                        if (refresh) {
                            newsRefresh.isRefreshing = false
                        } else {
                            loadingBar.visibility = View.GONE
                        }
                    }
                    Status.ERROR -> {
                        if (refresh) {
                            newsRefresh.isRefreshing = false
                            newsRv.visibility = View.VISIBLE
                        } else {
                            newsRv.visibility = View.VISIBLE
                            loadingBar.visibility = View.GONE
                        }
                        Toast.makeText(view.context, "Koneksi bermasalah!", Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        if (refresh) {
                            newsRv.visibility = View.GONE
                            newsList.clear()
                            newsAdapter.notifyDataSetChanged()
                        } else {
                            loadingBar.visibility = View.VISIBLE
                            newsRv.visibility = View.GONE
                        }
                    }
                }
            }
        })
    }
}