package com.example.myapplication.auth.data

import okhttp3.*
import kotlinx.coroutines.runBlocking

class TokenAuthenticator(private val tokenManager: SecureLoginDataStoreServices) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val currentToken = runBlocking { tokenManager.getAccessToken() }
        // Prevent infinite loop: If token refresh fails, do not retry
        if (response.request.header("authorization") == "Bearer $currentToken") {
            val newToken = runBlocking { tokenManager.getNewAccessToken() } // Get new access token
            return response.request.newBuilder()
                .header("authorization", "Bearer $newToken")
                .build()
        }
        return null
    }
}
