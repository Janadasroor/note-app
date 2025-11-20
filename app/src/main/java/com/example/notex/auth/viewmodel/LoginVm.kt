package com.example.notex.auth.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.notex.auth.data.LoginState
import com.example.notex.auth.data.RetrofitInstance
import com.example.notex.auth.data.SecureLoginDataStoreServices
import com.example.notex.auth.data.loginrepo.LoginRepo
import com.example.notex.auth.domain.usecase.auth.LoginCase
import com.example.notex.auth.domain.usecase.auth.LoginUseClass
import com.example.notex.auth.utils.validateEmail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginVm(private val loginUseClass: LoginUseClass) : ViewModel() {
    private  val _toastMessage=MutableLiveData<String>()
    val toastMessage:LiveData<String> =_toastMessage
    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState
    var errorPassword by mutableStateOf<String?>(null)
    var errorEmail by mutableStateOf<String?>(null)

    private var isRequestInProgress = false

    fun onEmailChanged(email:String){
        viewModelScope.launch {
            errorEmail= validateEmail(email)
        }
    }
   fun onPassChanged(password:String){
        viewModelScope.launch {
            delay(500)
            errorPassword = if (password.isEmpty()){
                "Enter your password"
            } else null
        }
    }

    fun sendCodeVerification(code:String,context: Context){
        if (isRequestInProgress) {
            Log.d("LoginViewModel", "Request already in progress, ignoring duplicate request.")
            return
        }
        _loginState.postValue( LoginState.Loading)
        isRequestInProgress = true  // Mark request as in progress

        viewModelScope.launch (Dispatchers.IO){
            try{
                val response =RetrofitInstance(context).api.verifyEmail(code)

                if(response.isSuccessful){
                    _loginState.postValue(LoginState.Success(response.body()?.message?:"Code is sent"))
                }
                else {
                    val errorMessage =

                        try{
                            val errorBody=   response.errorBody()?.string() ?: "Unknown error occurred"

                            JSONObject(errorBody ).getString("msg")

                        }
                        catch (
                            e:Exception
                        ){
                            "Unknown error occurred"
                        }
                    _loginState.postValue(LoginState.Error(errorMessage))

                }
            }
            catch (e:Exception){
             Log.d("msg",e.message.toString())
            }
            finally {
                isRequestInProgress=false
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun login( userViewModel: UserViewModel,email: String, password: String, profilePicture: String? = null,context: Context) {
        val dataServices = SecureLoginDataStoreServices(context = context)
        var msg=""
        if (isRequestInProgress) {
            Log.d("LoginViewModel", "Request already in progress, ignoring duplicate request.")
            return
        }

        if (email.isBlank() || password.isBlank()) {
            msg="All fields are required"
            _loginState.postValue(LoginState.Error(msg))
           _toastMessage.postValue(msg)
            return
        }

        _loginState.postValue( LoginState.Loading)
        isRequestInProgress = true  // Mark request as in progress

        viewModelScope.launch (Dispatchers.IO){
            try {
                Log.d("RegisterViewModel", "Sending request with Retrofit...")

                val response =loginUseClass.loginCase.execute(
                    email = email,
                    password = password
                )

                if (response.isSuccessful) {
                     msg=response.body()?.message ?: "Registration successful"
                    _loginState.postValue (LoginState.Success(msg))
                    _toastMessage.postValue(msg)
                        response.body()?.let {
                            dataServices.updateLoginInfo(
                                token = it.refreshToken,
                                accessToken = it.accessToken,
                                email = it.email,
                                id = it.id,
                                name = it.name
                            )
                            userViewModel.setToken(
                                it.accessToken,

                            )
                            userViewModel.setEmail(email = it.email)
                        }
                     //   Toast.makeText(context, "token ${ userViewModel.token.value }",Toast.LENGTH_LONG).show()


                    userViewModel.getLoginInfoFromStorage()

                    Log.e("Response"," ${ response.body() }")
                }
                else {
                    val errorBody=   response.errorBody()?.string() ?: "Unknown error occurred"

                    val errorMessage =

                        try{

                            JSONObject(errorBody ).getString("msg")

                        }
                        catch (
                            e:Exception
                        ){
                            "Unknown error occurred"
                        }
                    val isVerified =

                        try{

                            JSONObject(errorBody ).getInt("verified")

                        }
                        catch (
                            e:Exception
                        ){
                            1
                        }
                    println("Error Body is : ${errorMessage}")
                    println("Error Body is : ${errorBody}")


                    println("isVerified: $isVerified")


                    println(" isVerified : ${isVerified}")
                    Log.e("isVerified","${isVerified}")
                    Log.e("isVerified","${isVerified}")
                    Log.e("isVerified","${isVerified}")
                    Log.e("isVerified","${isVerified}")
                    _loginState.postValue( LoginState.Error(errorMessage,isVerified))
                    _toastMessage.postValue(msg)
                }

            } catch (e: Exception) {
                Log.e("RegisterViewModel", "Error during registration", e)
                msg="Registration failed: ${e.localizedMessage}"
                _loginState.postValue( LoginState.Error(msg))
                _toastMessage.postValue(msg)

            } finally {
                isRequestInProgress = false  // Reset flag after completion
            }
        }
    }
}

class LoginVmFact(private val context: Context):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val loginUseClass=LoginUseClass(
            loginCase = LoginCase(LoginRepo(context))
        )
        return LoginVm(loginUseClass) as T
    }
}

// Define different register states

