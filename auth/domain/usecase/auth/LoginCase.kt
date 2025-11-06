package com.example.myapplication.auth.domain.usecase.auth

import com.example.myapplication.auth.data.LoginResModel
import com.example.myapplication.auth.data.loginrepo.LoginRepo
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