package com.hadeer.domain.repoImpl

import android.content.Context
import com.google.gson.Gson
import com.hadeer.domain.ApiService
import com.hadeer.domain.CheckNetworkConnection
import com.hadeer.domain.entities.NetworkResponse
import com.hadeer.domain.entities.errors.ErrorResponseModal
import com.hadeer.domain.entities.home.events.EventResponseModal
import com.hadeer.domain.entities.home.events.toGetItemData
import com.hadeer.domain.repo.HomeRepo
import java.io.IOException
import javax.inject.Inject

class HomeRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val context: Context
) : HomeRepo
{
    override suspend fun getEvents(): NetworkResponse<List<EventResponseModal>> {
        if(CheckNetworkConnection.checkConnectivity(context)){
            val response = apiService.getEvents()
            if(response.isSuccessful){
                println("income data ${response.body()!!.data}" )
                return NetworkResponse.Success(
                    response.body()?.data?.mapNotNull {
                        it?.toGetItemData()
                    }?: emptyList()
                )
            }
            else if(response.code() == 400){
                val gson = Gson()
                val error = gson.fromJson(
                    response.errorBody()!!.charStream(),
                    ErrorResponseModal::class.java
                )
                println("error 400 is ${error.message!!}")
                return NetworkResponse.ApiError(
                    error.message, 400
                )
            }
            else if(response.code() == 401){
                println("error 401 is ${response}")
                return NetworkResponse.ApiError(
                    "Your session is finished please relogin",
                    401
                )
            }
            else if(response.code() == 404){
                println("error 404 is ${response}")
                return NetworkResponse.ApiError(
                    "This account doesn't exist",
                    404
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