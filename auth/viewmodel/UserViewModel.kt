package com.example.myapplication.auth.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.auth.data.LoginPrefModel
import com.example.myapplication.auth.data.MediaPost
import com.example.myapplication.auth.data.userrepo.UserRepository
import com.example.myapplication.auth.domain.usecase.user.GetLoginInfoUseCase
import com.example.myapplication.auth.domain.usecase.user.LoadPostsUseCase
import com.example.myapplication.auth.domain.usecase.user.LogoutUseCase
import com.example.myapplication.auth.domain.usecase.user.UserUseClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class UserViewModel(
    private  val userUseClass: UserUseClass,
    private val context: Context ):ViewModel(){
    var secureData = MutableLiveData<LoginPrefModel?>()
    val posts = MutableLiveData<List<MediaPost>?>()
    val postsString = MutableLiveData<String>()
    val isLoaded = MutableStateFlow(false)
    val isLogin=MutableStateFlow(false)


//   private  val _token = MutableStateFlow("")
//    val token:StateFlow<String> =_email
    init {
       getLoginInfoFromStorage()
       getSettingsInfoFromStorage()
    }

     fun getSettingsInfoFromStorage() {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

    private val _token = MutableStateFlow("")
    val token: StateFlow<String> = _token
    fun setToken(token: String) {
        _token.value = token
    }
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    fun setEmail(email: String) {
        _email.value = email
    }


    fun logout(){
      viewModelScope.launch {
          userUseClass.logoutUseCase()
          isLogin.value=false
      }
    }
    fun getLoginInfoFromStorage() {
        viewModelScope.launch {
            secureData.value = userUseClass.getLoginInfoUseCase()
            if(secureData.value!=null){
                isLogin.value=true
            }else{
                isLogin.value=false

            }
            isLoaded.value = true


        }
    }

    fun loadData(accessToken: String) {
        viewModelScope.launch {
            try {
                val postList =userUseClass. loadPostsUseCase(accessToken)
                posts.postValue(postList)
                postsString.postValue(postList?.toString() ?: "Success")
                showToast("Loaded ${postList?.size ?: 0} posts")
            } catch (e: Exception) {
                showToast("Error: ${e.message}")
            }
        }
    }
    private fun showToast(msg: String) {
        viewModelScope.launch(Dispatchers.Main) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }
}


class UserViewModelFactory(private  val context: Context) :ViewModelProvider.Factory{
    @SuppressLint("SuspiciousIndentation")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val userRepo=UserRepository(context)
        val userUseClass=UserUseClass(
            getLoginInfoUseCase = GetLoginInfoUseCase(userRepo),
            loadPostsUseCase = LoadPostsUseCase(userRepo),
            logoutUseCase = LogoutUseCase(userRepo)
        )
        return  UserViewModel(userUseClass,context)  as T
    }
}