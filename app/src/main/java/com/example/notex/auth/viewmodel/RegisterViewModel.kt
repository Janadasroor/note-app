package com.example.notex.auth.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notex.auth.data.RegisterReqModel
import com.example.notex.auth.data.RegisterState
import com.example.notex.auth.data.RetrofitInstance
import com.example.notex.auth.data.SecureLoginDataStoreServices
import com.example.notex.auth.utils.validateEmail
import com.example.notex.auth.utils.validateName
import com.example.notex.auth.utils.validatePass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class RegisterViewModel : ViewModel() {


    private val _registerState = MutableLiveData<RegisterState>()
    val registerState: LiveData<RegisterState> = _registerState
    var errorPassword = MutableLiveData<String?>(null)
    var errorEmail by mutableStateOf<String?>(null)
    var errorName by mutableStateOf<String?>(null)

    private var isRequestInProgress = false
    fun onEmailChanged(email:String){
        viewModelScope.launch (Dispatchers.Main){
            errorEmail= validateEmail(email)
        }
    }
    fun onPassChanged(password:String){
        viewModelScope.launch(Dispatchers.Main) {
            errorPassword.postValue(validatePass(password))
        }
    }
     fun onNameChanged(name:String){
            viewModelScope.launch(Dispatchers.Main) {
                errorName= validateName(name)
            }
        }

    fun sendCodeVerification(code:String,context: Context,userViewModel: UserViewModel){
        val dataServices = SecureLoginDataStoreServices(context = context)

        if (isRequestInProgress) {
            Log.d("RegisterViewModel", "Request already in progress, ignoring duplicate request.")
            return
        }
        _registerState.postValue( RegisterState.Loading)
        isRequestInProgress = true  // Mark request as in progress

        viewModelScope.launch (Dispatchers.IO){
            try{
                val response =RetrofitInstance(context).api.verifyEmail(code)

                if(response.isSuccessful){
                    _registerState.postValue(RegisterState.Success(response.body()?.message?:"Code is sent"))
                    withContext(Dispatchers.Main){
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
                        Toast.makeText(context, "token ${ userViewModel.token.value }",Toast.LENGTH_LONG).show()

                    }
                    userViewModel.getLoginInfoFromStorage()

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
                    _registerState.postValue(RegisterState.Error(errorMessage))

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


    fun resendCodeVerification(email: String,context: Context){
        if (isRequestInProgress) {
            Log.d("RegisterViewModel", "Request already in progress, ignoring duplicate request.")
            return
        }

        viewModelScope.launch (Dispatchers.IO){
            try{
                val response =RetrofitInstance(context).api.resendCode(mapOf("email" to email))

                if(response.isSuccessful){
                 Toast.makeText(context,response.body()?.msg?:"sent",Toast.LENGTH_LONG).show()

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
                    Toast.makeText(context,errorMessage,Toast.LENGTH_LONG).show()

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
    fun register(context:Context,userViewModel: UserViewModel,name: String, email: String, password: String, profilePicture: String? = null) {
        if (isRequestInProgress) {
            Log.d("RegisterViewModel", "Request already in progress, ignoring duplicate request.")
            return
        }

        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            _registerState.postValue(RegisterState.Error("All fields are required"))
            return
        }

        _registerState.postValue( RegisterState.Loading)
        isRequestInProgress = true  // Mark request as in progress

        viewModelScope.launch (Dispatchers.IO){
            try {
                Log.d("RegisterViewModel", "Sending request with Retrofit...")

                val response = RetrofitInstance(context).api.registerUser(
                    RegisterReqModel(email = email, name = name, password = password)
                )

                if (response.isSuccessful) {
                    _registerState.postValue (RegisterState.Success(response.body()?.msg ?: "Registration successful"))

                    withContext(Dispatchers.Main){
                        userViewModel.setEmail(
                            email
                        )
                         Toast.makeText(context, "send to ${ userViewModel.email.value }",Toast.LENGTH_LONG).show()

                    }
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
                    _registerState.postValue( RegisterState.Error(errorMessage))

                }

            } catch (e: Exception) {
                Log.e("RegisterViewModel", "Error during registration", e)
                _registerState.postValue( RegisterState.Error("Registration failed: ${e.localizedMessage}"))
            } finally {
                isRequestInProgress = false  // Reset flag after completion
            }
        }
    }
}


// Define different register states
//sealed class RegisterState {
//    object Loading : RegisterState()
//    data class Success(val message: String) : RegisterState()
//    data class Error(val message: String) : RegisterState()
//}
