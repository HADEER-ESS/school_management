package com.hadeer.domain.repoImpl

import android.content.Context
import com.google.gson.Gson
import com.hadeer.domain.ApiService
import com.hadeer.domain.CheckNetworkConnection
import com.hadeer.domain.entities.NetworkResponse
import com.hadeer.domain.entities.errors.ErrorResponseModal
import com.hadeer.domain.entities.login.LoginBody
import com.hadeer.domain.entities.login.LoginResponse
import com.hadeer.domain.repo.LoginRepo
import java.io.IOException
import javax.inject.Inject

class LoginRepoImpl @Inject constructor(
    private val apiService : ApiService,
    private val context : Context
):LoginRepo {
    override suspend fun login(loginBody: LoginBody): NetworkResponse<LoginResponse> {
//        Check internet connectivity
        if(CheckNetworkConnection.checkConnectivity(context)){
        println("enter api call $loginBody")
            val response = apiService.login(loginBody)

            if(response.isSuccessful){
                return NetworkResponse.Success(response.body()!!)
            }
            else if(response.code() == 400){
                val gson = Gson()
                val error = gson.fromJson(
                    response.errorBody()!!.charStream(),
                    ErrorResponseModal::class.java
                )
                println("error 400 is ${error.message!!}")
                return NetworkResponse.ApiError(
                    error.message!!, 400
                )
            }
            else if(response.code() == 401){
                println("error 401 is ${response}")
                return NetworkResponse.ApiError(
                    "Your session is finished please relogin",
                    401
                )
            }
            else{
                println("error other wise is ${response }")
                return NetworkResponse.ApiError("error" , response.code())
            }
        }
        else{
            return NetworkResponse.NetworkError(
                IOException(
                    "No internet connection, please check your connectivity"
                )
            )
        }
    }
}