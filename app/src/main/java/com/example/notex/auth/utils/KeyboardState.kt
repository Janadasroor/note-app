package com.example.notex.auth.utils

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp

@Composable
fun rememberKeyboardVisibility(): State<Boolean> {
    val view = LocalView.current
    val density = LocalDensity.current
    val keyboardVisibleState = remember { mutableStateOf(false) }

    DisposableEffect(view) {
        val rootView = view.rootView
        val rect = Rect()
        val threshold = with(density) { 100.dp.toPx().toInt() }

        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.height
            val visibleHeight = rect.height()
            val heightDiff = screenHeight - visibleHeight
            keyboardVisibleState.value = heightDiff > threshold
        }

        rootView.viewTreeObserver.addOnGlobalLayoutListener(listener)

        onDispose {
            rootView.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }

    return keyboardVisibleState
}
