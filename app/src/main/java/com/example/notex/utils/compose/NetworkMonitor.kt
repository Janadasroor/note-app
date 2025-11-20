package com.example.notex.utils.compose

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.notex.utils.NetworkReceiver
import com.example.notex.utils.checkNetworkAccess

@Composable
fun NetworkMonitorToast() {
    val context = LocalContext.current
    val isConnected = remember { mutableStateOf(checkNetworkAccess(context)) }

    DisposableEffect(Unit) {
        val receiver = NetworkReceiver { status ->
            if (isConnected.value != status) {
                isConnected.value = status
                val msg = if (status) "The connection has been restored" else "You are offline"
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }

        val intentFilter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        } else {
            @Suppress("DEPRECATION")
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        }

        context.registerReceiver(receiver, intentFilter)

        onDispose {
            try {
                context.unregisterReceiver(receiver)
            } catch (e: IllegalArgumentException) {
                // Receiver was already unregistered
            }
        }
    }
}