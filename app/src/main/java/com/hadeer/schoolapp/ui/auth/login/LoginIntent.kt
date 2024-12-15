package com.hadeer.schoolapp.ui.auth.login

sealed class LoginIntent {
    data class Ideal(val state : LoginState) : LoginIntent()
    data class InputFailed(val state: LoginState) : LoginIntent()
    data class Success(val state: LoginState) : LoginIntent()
    data class Failed(val state: LoginState) : LoginIntent()
}