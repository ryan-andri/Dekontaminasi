package id.ryanandri.dekontaminasi.ui.about

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import id.ryanandri.dekontaminasi.R
import id.ryanandri.dekontaminasi.custom.VmFactoryHandler
import id.ryanandri.dekontaminasi.services.RetrofitService
import id.ryanandri.dekontaminasi.services.Status
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.loading.*
import retrofit2.Response

class AboutFragment : Fragment() {

    private lateinit var aboutViewModel: AboutViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        aboutViewModel = ViewModelProvider(this,
                VmFactoryHandler(RetrofitService.aboutService, "about"))
                .get(AboutViewModel::class.java)
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData(view)
    }

    private fun observeData(view: View) {
        aboutViewModel.getAboutData().observe(viewLifecycleOwner, {
            it?.let { resources ->
                when (resources.status) {
                    Status.SUCCESS -> {
                        loadingBar.visibility = View.GONE
                        val result : Response<AboutResponse> = resources.data as Response<AboutResponse>
                        Glide.with(view.context)
                                .load(result.body()?.avatarUrl)
                                .override(480, 480)
                                .circleCrop()
                                .into(profile_img)
                        about_name.text = result.body()?.name
                        about_loc.text = result.body()?.location
                        about_blog.text = result.body()?.blog
                    }
                    Status.ERROR -> {
                        loadingBar.visibility = View.GONE
                        Toast.makeText(view.context, "Koneksi bermasalah!", Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        loadingBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }
}