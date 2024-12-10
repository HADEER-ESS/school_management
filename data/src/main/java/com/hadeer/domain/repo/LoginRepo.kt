package com.hadeer.domain.repo

import com.hadeer.domain.entities.NetworkResponse
import com.hadeer.domain.entities.login.LoginBody
import com.hadeer.domain.entities.login.LoginResponse

interface LoginRepo {
    suspend fun login(loginBody: LoginBody) : NetworkResponse<LoginResponse>
}