package com.hadeer.schoolapp.ui.auth.login

data class LoginState(
//    Loading
    val isLoading : Boolean = false,
//    Fail
    val emailErrorText : String = "",
    val passwordErrorString : String = "",
    val roleErrorString : String = "",
    val loginApiError : String = "",
//    Success
    val isLoginBtnEnabled : Boolean = false,
    val isLoginSuccess : Boolean = false,
)
