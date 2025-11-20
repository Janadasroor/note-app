package com.example.notex.utils

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresPermission

class NetworkReceiver(private val onStatusChanged: (Boolean) -> Unit) : BroadcastReceiver() {
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val isConnected = checkNetworkAccess(it)
            onStatusChanged(isConnected)
        }
    }
}

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun checkNetworkAccess(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            ?: return false

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        // For API 23 and above
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    } else {
        // For older versions (API < 23)
        @Suppress("DEPRECATION")
        val activeNetwork = connectivityManager.activeNetworkInfo
        @Suppress("DEPRECATION")
        activeNetwork?.isConnected == true
    }
}