package com.hadeer.domain.entities.errors

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("errors")
	val errors: Errors? = null
)

data class Errors(

	@field:SerializedName("email")
	val email: List<String?>? = null,

	@field:SerializedName("password")
	val password: List<String?>? = null,

	@field:SerializedName("role")
	val role : List<String?>? = null
)
