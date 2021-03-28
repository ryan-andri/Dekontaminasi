package id.ryanandri.dekontaminasi.ui.news

import com.google.gson.annotations.SerializedName

data class NewsListResponse(

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: Long? = null
)
