package com.example.notex.auth.data.userrepo

import android.content.Context
import android.util.Log
import com.example.notex.auth.data.LoginPrefModel
import com.example.notex.auth.data.MediaPost
import com.example.notex.auth.data.RetrofitInstance
import com.example.notex.auth.data.SecureLoginDataStoreServices
import com.example.notex.settings.data.models.SettingsPrefModel
import com.example.notex.settings.settingmanager.SecureSettingDataManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.json.JSONObject

class UserRepository (
    private  val context: Context
) {


    suspend fun getLoginInfo(): LoginPrefModel? {

        return SecureLoginDataStoreServices(context).getLoginInfo()
    }


    suspend fun logout(){
        SecureLoginDataStoreServices(context).updateLoginInfo(
            token = "",
            email = "",
            id = "",
            accessToken = "",
            name = "",
        )
    }

    suspend fun getProtectedData(token: String): List<MediaPost>? {
        val response = RetrofitInstance(context).api.getProtectedData("Bearer $token")
        Log.e("LOG", "Fetching data...")

        withContext(Dispatchers.Main) { // Switch to main thread
            Log.e("LOG", "Fetching data...")

        }

        if (response.isSuccessful) {
            withContext(Dispatchers.Main) {
                response.body()
            }

            //  contentLoadMsg.value = response.body()?.data?.get(0)?.user_id ?: "Empty"
        } else {

            val errorBody = response.errorBody()?.string() ?: "Unknown error occurred"

            val errorMessage = try {
                JSONObject(errorBody).getString("error")

            } catch (e: Exception) {
                "Unknown error"
            }
            throw Exception(errorMessage)

        }
        return null
    }

}

