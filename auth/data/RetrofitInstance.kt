package com.example.myapplication.auth.data


import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class RetrofitInstance (context: Context){

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Logs request & response body
        })
        .authenticator(TokenAuthenticator(SecureLoginDataStoreServices(context)))
        .build()

    val api: RegisterApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RegisterApiService::class.java)
    }

    companion object {
        private const val BASE_URL = "http://10.0.2.2:3000/" // Localhost for emulator
    }
}
