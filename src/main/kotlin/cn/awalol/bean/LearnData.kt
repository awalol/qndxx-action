package cn.awalol.bean

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class LearnData(

	@field:JsonProperty("isLearned")
	val isLearned: Boolean? = null,

	@field:JsonProperty("learnContent")
	val learnContent: LearnContent? = null,
)

data class LearnContent(

	@field:JsonProperty("brief")
	val brief: Any? = null,

	@field:JsonProperty("titleSub")
	val titleSub: String? = null,

	@field:JsonProperty("expdate_str")
	val expdateStr: String? = null,

	@field:JsonProperty("id")
	val id: Int? = null,

	@field:JsonProperty("title")
	val title: String? = null,

	@field:JsonProperty("pubdate_str")
	val pubdateStr: String? = null,

	@field:JsonProperty("content")
	val content: String? = null,

	@field:JsonProperty("pubdate")
	val pubdate: String? = null,

	@field:JsonProperty("expdate")
	val expdate: String? = null,

	@field:JsonProperty("isExp")
	val isExp: Boolean? = null
)
