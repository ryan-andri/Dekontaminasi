package id.ryanandri.dekontaminasi.ui.stats

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ryanandri.dekontaminasi.R
import kotlinx.android.synthetic.main.list_item_stats.view.*

class StatsRecyclerView(
        private val context : Context,
        private val regionsItem : List<RegionsItem?>?,
) : RecyclerView.Adapter<StatsRecyclerView.StatsView>() {

    private var listRegion : List<RegionsItem?>? = regionsItem

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsView {
        return StatsView(LayoutInflater.from(context)
                .inflate(R.layout.list_item_stats, parent, false))
    }

    override fun onBindViewHolder(holder: StatsView, position: Int) {
        holder.bindItems(listRegion!![position]!!)
    }

    override fun getItemCount(): Int {
        return listRegion!!.size
    }

    class StatsView(private val bindView : View) : RecyclerView.ViewHolder(bindView) {
        fun bindItems(item : RegionsItem) {
            bindView.apply {
                val numbers : Numbers? = item.numbers
                regionName.text = item.name
                regionInfected.text = numbers?.infected.toString()
                regionRecovered.text = numbers?.recovered.toString()
                regionFatal.text = numbers?.fatal.toString()
            }
        }
    }
}