package id.ryanandri.dekontaminasi.ui.about

import com.google.gson.annotations.SerializedName

data class AboutResponse(

	@field:SerializedName("blog")
	val blog: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("html_url")
	val htmlUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: String? = null,
)
