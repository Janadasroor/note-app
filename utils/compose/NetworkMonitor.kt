package com.example.myapplication.utils.compose

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.utils.NetworkReceiver

@Composable
fun NetworkMonitorToast() {
    val context = LocalContext.current
    val isConnected = remember { mutableStateOf(true) }

    DisposableEffect(Unit) {
        val receiver = NetworkReceiver { status ->
            isConnected.value = status
            val msg = if (status) "The connection has been restored" else "You are offline"
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(receiver, intentFilter)

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }
}
