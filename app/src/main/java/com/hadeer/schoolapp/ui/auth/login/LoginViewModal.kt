package com.hadeer.schoolapp.ui.auth.login

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadeer.domain.TokenProvider
import com.hadeer.domain.entities.NetworkResponse
import com.hadeer.domain.entities.login.LoginBody
import com.hadeer.domain.useCase.LoginUseCase
import com.hadeer.domain.utils.SecurSharedPrefs
import com.hadeer.domain.utils.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
@HiltViewModel
class LoginViewModal @Inject constructor(
    private val loginUseCase : LoginUseCase,
    private val context: Application,
    private val tokenProvider: TokenProvider
): ViewModel() {
    private var initState = LoginState()
    private val _loginIntent = MutableSharedFlow<LoginIntent>()
    var loginIntent = _loginIntent.asSharedFlow()

    fun loginUser(email : String, password : String, role : String){
        viewModelScope.launch {
            println("income data is $email $password $role")
            initState = initState.copy(
                isLoading = true,
                isLoginBtnEnabled = false
            )
            _loginIntent.emit(
                LoginIntent.Ideal(initState)
            )
            if(!checkGmailRegexValidation(email)){
                initState = initState.copy(
                    isLoginSuccess = false,
                    isLoginBtnEnabled = false,
                    emailErrorText = "Invalid Email Address"
                )
                _loginIntent.emit(
                    LoginIntent.InputFailed(initState)
                )
            }
            if(password.length < 6){
                initState = initState.copy(
                    isLoading = false,
                    isLoginBtnEnabled = false,
                    passwordErrorString = "Password must be at least 6 characters"
                )
                _loginIntent.emit(
                    LoginIntent.InputFailed(initState)
                )
            }
            else{
                val response = loginUseCase.loginRequest(
                    LoginBody(
                        email = email,
                        password = password,
                        role = role[0].toLowerCase()+ role.substring(1)
                    )
                )
            println("the response is $response" )
                when(response){
                    is NetworkResponse.Success -> {
//                        Store Access-Token in secure shared preference
                        saveIncomeSecureSuccessData(response.body.data?.accessToken)
                        saveIncomeStringSuccessData("user_name", response.body.data?.name!!)
                        saveIncomeIntSuccessData("user_id", response.body.data?.id!!)

                        initState = initState.copy(
                            isLoading = false,
                            isLoginSuccess = true
                        )
                        _loginIntent.emit(
                            LoginIntent.Success(initState)
                        )
                    }
                    is NetworkResponse.ApiError -> {
                        initState = initState.copy(
                            isLoading = false,
                            loginApiError = response.body,
                            isLoginSuccess = false,
                        )
                        _loginIntent.emit(
                            LoginIntent.Failed(initState)
                        )
                    }
                    is NetworkResponse.NetworkError -> {
                        initState = initState.copy(
                            isLoading = false,
                            loginApiError = "You internet is not stable, try again later"
                        )
                        _loginIntent.emit(
                            LoginIntent.Failed(initState)
                        )
                    }
                    is NetworkResponse.UnknowError -> {
                        initState = initState.copy(
                            isLoading = false,
                            loginApiError = "You internet is not stable, try again later"
                        )
                        _loginIntent.emit(
                            LoginIntent.Failed(initState)
                        )
                    }
                }
            }
        }
    }

    private fun checkGmailRegexValidation(email : String):Boolean{
        val regex = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        return regex.matches(email)
    }

    private fun saveIncomeSecureSuccessData(value : String?){
        tokenProvider.setToken(value!!)
    }

    private fun saveIncomeStringSuccessData(key :String, value : String?){
        SharedPrefs.saveStringData(context, key,value)
    }

    private fun saveIncomeIntSuccessData(key: String, value : Int?){
        SharedPrefs.saveIntData(context, key, value)
    }
}