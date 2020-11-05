package id.ryanandri.dekontaminasi.ui.rs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import id.ryanandri.dekontaminasi.R
import kotlinx.android.synthetic.main.list_item_rs.view.*
import java.util.*

class RsRecyclerView(
        private val context : Context,
        private val rsItem : List<RsListResponse>,
) : RecyclerView.Adapter<RsRecyclerView.RsView>(), Filterable {

    private var listRegion : List<RsListResponse> = rsItem

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RsView {
        return RsView(LayoutInflater.from(context)
                .inflate(R.layout.list_item_rs, parent, false))
    }

    override fun onBindViewHolder(holder: RsView, position: Int) {
        holder.bindItems(listRegion[position])
    }

    override fun getItemCount(): Int {
        return listRegion.size
    }

    class RsView(private val bindView : View) : RecyclerView.ViewHolder(bindView) {
        fun bindItems(item : RsListResponse) {
            bindView.apply {
                namaRs.text = item.name
                alamatRs.text = item.address
                kotaRs.text = item.region
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
                rsItem
            } else {
                rsItem.filter { it.name?.toLowerCase(Locale.ROOT)?.contains(input)!!
                            || it.region?.toLowerCase(Locale.ROOT)?.contains(input)!!}
            }

            return result
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            listRegion = p1?.values as List<RsListResponse>
            notifyDataSetChanged()
        }
    }
}