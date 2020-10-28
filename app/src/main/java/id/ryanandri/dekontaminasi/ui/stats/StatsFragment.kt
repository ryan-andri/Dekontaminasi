package id.ryanandri.dekontaminasi.ui.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.ryanandri.dekontaminasi.R

class StatsFragment : Fragment() {

    private lateinit var statsViewModel: StatsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        statsViewModel =
                ViewModelProvider(this).get(StatsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_stats, container, false)
        val textView: TextView = root.findViewById(R.id.text_stats)
        statsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}