package id.ryanandri.dekontaminasi.ui.news

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import id.ryanandri.dekontaminasi.R
import id.ryanandri.dekontaminasi.utils.TimeStamp2Date
import kotlinx.android.synthetic.main.list_item_news.view.*

class NewsRecyclerView(
        private val context : Context,
        private val newsItems : List<NewsListResponse>,
) : RecyclerView.Adapter<NewsRecyclerView.NewsView>() {

    private var newsItem : List<NewsListResponse> = newsItems

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsView {
        return NewsView(LayoutInflater.from(context)
                .inflate(R.layout.list_item_news, parent, false))
    }

    override fun onBindViewHolder(holder: NewsView, position: Int) {
        holder.bindItems(newsItem[position])
    }

    override fun getItemCount(): Int {
        return newsItem.size
    }

    class NewsView(private val bindView : View) : RecyclerView.ViewHolder(bindView) {
        fun bindItems(item : NewsListResponse) {
            bindView.apply {

                newsTitle.text = item.title
                newsDate.text = TimeStamp2Date().convert(item.timestamp)

                newsCard.setOnClickListener {
                    Toast.makeText(it.context, item.title, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}