package id.ryanandri.dekontaminasi.ui.stats

import com.google.gson.annotations.SerializedName

data class StatsResponse(

	@field:SerializedName("regions")
	val regions: List<RegionsItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("numbers")
	val numbers: Numbers? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: Long? = null
)

data class RegionsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("numbers")
	val numbers: Numbers? = null,

	@field:SerializedName("type")
	val type: String? = null
)

data class Numbers(

	@field:SerializedName("infected")
	val infected: Int? = null,

	@field:SerializedName("recovered")
	val recovered: Int? = null,

	@field:SerializedName("fatal")
	val fatal: Int? = null
)
