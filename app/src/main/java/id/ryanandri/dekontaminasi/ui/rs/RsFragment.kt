package id.ryanandri.dekontaminasi.ui.rs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.ryanandri.dekontaminasi.R

class RsFragment : Fragment() {

    private lateinit var rsViewModel: RsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        rsViewModel =
                ViewModelProvider(this).get(RsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_rs, container, false)
        val textView: TextView = root.findViewById(R.id.text_rs)
        rsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}