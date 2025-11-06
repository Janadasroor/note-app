package com.example.myapplication.utils


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import okhttp3.*
import java.io.IOException


class GoogleAuthViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext
    private val client = OkHttpClient()

    // Google Sign-In Client
    val googleSignInClient: GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("777199134518-ese8nqoo5upv6ev1j1frir7mj6r73o5k.apps.googleusercontent.com")
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }

    // Handle Google Sign-In result
    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account?.idToken
            idToken?.let {
                sendTokenToServer(it)
            }
        } catch (e: ApiException) {
            Log.e("GoogleSignIn", "Sign-in failed", e)
        }
    }

    // Send token to server
    private fun sendTokenToServer(idToken: String) {
        val requestBody = FormBody.Builder()
            .add("idToken", idToken)
            .build()

        val request = Request.Builder()
            .url("http://10.0.2.2:3000/auth/google")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ServerAuth", "Failed to send token", e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let {
                    Log.d("ServerAuth", "Server Response: ${it.string()}")
                }
            }
        })
    }
}
