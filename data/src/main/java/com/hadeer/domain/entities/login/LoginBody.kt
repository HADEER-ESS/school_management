package com.hadeer.domain.entities.login

data class LoginBody(
    val email : String,
    val password : String,
    val role : String,
)
