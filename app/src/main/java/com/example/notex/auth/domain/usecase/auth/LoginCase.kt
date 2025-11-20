package com.example.notex.auth.domain.usecase.auth

import com.example.notex.auth.data.LoginResModel
import com.example.notex.auth.data.loginrepo.LoginRepo
import retrofit2.Response
import kotlin.math.log

class LoginCase (private val loginRepo: LoginRepo){
    suspend fun execute(email:String,password:String):Response<LoginResModel>{
       return loginRepo.login(
            email = email,
            password = password,
        )
    }
}