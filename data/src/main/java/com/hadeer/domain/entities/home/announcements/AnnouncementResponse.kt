package com.hadeer.domain.entities.home.announcements

import com.google.gson.annotations.SerializedName

data class AnnouncementResponse(

	@field:SerializedName("data")
	val data: AnnouncementData? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: Any? = null
)

data class LinksItem(

	@field:SerializedName("active")
	val active: Boolean? = null,

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("url")
	val url: Any? = null
)

data class Status(

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("value")
	val value: String? = null
)

data class Items(

	@field:SerializedName("per_page")
	val perPage: Int? = null,

	@field:SerializedName("data")
	val data: List<AnnouncementDataItem?>? = null,

	@field:SerializedName("last_page")
	val lastPage: Int? = null,

	@field:SerializedName("next_page_url")
	val nextPageUrl: Any? = null,

	@field:SerializedName("prev_page_url")
	val prevPageUrl: Any? = null,

	@field:SerializedName("first_page_url")
	val firstPageUrl: String? = null,

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("last_page_url")
	val lastPageUrl: String? = null,

	@field:SerializedName("from")
	val from: Int? = null,

	@field:SerializedName("links")
	val links: List<LinksItem?>? = null,

	@field:SerializedName("to")
	val to: Int? = null,

	@field:SerializedName("current_page")
	val currentPage: Int? = null
)

data class AnnouncementData(

	@field:SerializedName("query")
	val query: Any? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("items")
	val items: Items? = null
)

data class Slugable(

	@field:SerializedName("reference_id")
	val referenceId: Int? = null,

	@field:SerializedName("prefix")
	val prefix: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("key")
	val key: String? = null,

	@field:SerializedName("reference_type")
	val referenceType: String? = null
)

data class AnnouncementDataItem(

	@field:SerializedName("end_date")
	val endDate: Any? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("author_type")
	val authorType: String? = null,

	@field:SerializedName("slugable")
	val slugable: Slugable? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("format_type")
	val formatType: Any? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("state_id")
	val stateId: Int? = null,

	@field:SerializedName("author_id")
	val authorId: Int? = null,

	@field:SerializedName("is_featured")
	val isFeatured: Int? = null,

	@field:SerializedName("views")
	val views: Int? = null,

	@field:SerializedName("country_id")
	val countryId: Int? = null,

	@field:SerializedName("status")
	val status: Status? = null,

	@field:SerializedName("start_date")
	val startDate: Any? = null,

	@field:SerializedName("city_id")
	val cityId: Int? = null
)
