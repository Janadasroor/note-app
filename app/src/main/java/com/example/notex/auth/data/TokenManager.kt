package com.example.notex.auth.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class TokenManager(context: Context):ViewModel(){
    val secureLoginDataStoreServices= SecureLoginDataStoreServices(context)

    fun updateAccessToken(accessToken:String){
     viewModelScope.launch {
         secureLoginDataStoreServices.updateAccessToken(accessToken = accessToken)

     }
    }

    fun getAccessToken():String{
        var token=""
        viewModelScope.launch {
            token= secureLoginDataStoreServices.getAccessToken()

        }
         return token
    }

}