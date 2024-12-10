package com.hadeer.domain.useCase

import com.hadeer.domain.entities.NetworkResponse
import com.hadeer.domain.entities.login.LoginBody
import com.hadeer.domain.entities.login.LoginResponse
import com.hadeer.domain.repo.LoginRepo
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepo: LoginRepo
){
    suspend fun loginRequest(logingBody : LoginBody) : NetworkResponse<LoginResponse>{
        return loginRepo.login(logingBody)
    }
}