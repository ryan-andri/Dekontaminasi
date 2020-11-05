package id.ryanandri.dekontaminasi.ui.stats

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import id.ryanandri.dekontaminasi.R
import id.ryanandri.dekontaminasi.utils.ValueFormater
import kotlinx.android.synthetic.main.list_item_stats.view.*
import java.util.*

class StatsRecyclerView(
        private val context : Context,
        private val regionsItem : List<RegionsItem?>?,
) : RecyclerView.Adapter<StatsRecyclerView.StatsView>(), Filterable {

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
                regionName.text = item.name?.toUpperCase(Locale.ROOT)
                regionInfected.text = ValueFormater().decimalFormat(numbers?.infected)
                regionRecovered.text = ValueFormater().decimalFormat(numbers?.recovered)
                regionFatal.text = ValueFormater().decimalFormat(numbers?.fatal)
            }
        }
    }

    override fun getFilter(): Filter {
        return searchFilter
    }

    private val searchFilter: Filter = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val input : String = p0.toString().toLowerCase(Locale.ROOT).trim()
            val result = FilterResults()

            result.values = if (p0 == null || p0.isEmpty()) {
                regionsItem
            } else {
                regionsItem?.filter {
                    it?.name?.toLowerCase(Locale.ROOT)?.contains(input)!!}
            }

            return result
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            listRegion = p1?.values as List<RegionsItem?>?
            notifyDataSetChanged()
        }
    }
}