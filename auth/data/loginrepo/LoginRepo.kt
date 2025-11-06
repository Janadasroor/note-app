package com.example.myapplication.auth.data.loginrepo

import android.content.Context
import com.example.myapplication.auth.data.LoginReqModel
import com.example.myapplication.auth.data.LoginResModel
import com.example.myapplication.auth.data.RetrofitInstance
import com.example.myapplication.auth.data.SecureLoginDataStoreServices
import com.example.myapplication.auth.data.VerificationRes
import retrofit2.Response


class LoginRepo(private val context: Context){
    val dataServices = SecureLoginDataStoreServices(context = context)

    suspend fun login(
        email: String,
        password: String,
        profilePicture: String? = null
    ):Response<LoginResModel>{
        return RetrofitInstance(context).api.loginUser(
            LoginReqModel(email = email, password = password)
        )

    }
    suspend fun verifyEmail(code: String): Response<VerificationRes> {
        val api = RetrofitInstance(context).api
        return api.verifyEmail(code)
    }
}