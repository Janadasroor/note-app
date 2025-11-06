package com.example.myapplication.auth.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.myapplication.data.dataStore
import kotlinx.coroutines.flow.first

class SecureLoginDataStoreServices(private  val context: Context) {

    private val TOKEN_KEY = stringPreferencesKey("token")
    private val NAME_KEY = stringPreferencesKey("name")
    private val ACCESS_TOKEN_KEY = stringPreferencesKey("accessToken")
    private val EMAIL_KEY = stringPreferencesKey("email")
    private val USER_KEY = stringPreferencesKey("id")

    suspend fun updateLoginInfo( token: String,email:String,id:String,accessToken: String,name: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
            prefs[EMAIL_KEY] = email
            prefs[USER_KEY] = id
            prefs[ACCESS_TOKEN_KEY] = accessToken
            prefs[NAME_KEY] = name
        }
    }
     suspend fun updateAccessToken(accessToken:String){
         context.dataStore.edit {
             pre->
             pre[ACCESS_TOKEN_KEY]=accessToken
         }
     }
    suspend fun getNewAccessToken():String{
        val refreshTkn =context.dataStore.data.first()[TOKEN_KEY]?:""
       val response= RetrofitInstance(context).api.refreshToken(mapOf("refresh" to refreshTkn))
        if (response.isSuccessful){
            response.body()?.let { updateAccessToken(it.accessToken)
            }
        }
        return response.body()?.accessToken ?: ""
    }
     suspend fun getAccessToken():String{
         return context.dataStore.data.first()[ACCESS_TOKEN_KEY]?:""
     }
    suspend fun getUserID():String{
         return context.dataStore.data.first()[USER_KEY]?:""
     }

    suspend fun getLoginInfo(): LoginPrefModel? {
        val prefs = context.dataStore.data.first()
        val name=prefs[NAME_KEY]
        val token = prefs[TOKEN_KEY]
        val accessToken = prefs[ACCESS_TOKEN_KEY]
        val email = prefs[EMAIL_KEY]
        val userId = prefs[USER_KEY]

        return if (!token.isNullOrEmpty() && !name.isNullOrEmpty() && !accessToken.isNullOrEmpty() && !email.isNullOrEmpty() && !userId.isNullOrEmpty()) {
            LoginPrefModel(
                name =name,
                token = token,
                accessToken = accessToken,
                email = email,
                id = userId
            )
        } else {
            null
        }
    }

}

data class LoginPrefModel(
    val name: String,
    val token: String,
    val accessToken: String,
    val email: String,
    val id: String
)