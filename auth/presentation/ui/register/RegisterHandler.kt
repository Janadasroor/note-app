package com.example.myapplication.auth.presentation.ui.register

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.auth.data.RegisterState
import com.example.myapplication.auth.presentation.ui.theme.btnFont
import com.example.myapplication.auth.viewmodel.UserViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HandleRegisterState(
    userViewModel: UserViewModel,
    navController: NavController,
    context: Context,
    registerState: RegisterState?
) {
    when (registerState) {
        is RegisterState.Loading -> CircularProgressIndicator()

        is RegisterState.Success -> {

            Text(registerState.message, color = Color.Green)
            LaunchedEffect(Unit) {  Toast.makeText(context, registerState.message, Toast.LENGTH_LONG).show()
            navController.navigate("verify"){
                popUpTo("add"){
                    inclusive =true
                }
            }}
        }


        is RegisterState.Error -> {
            Text(registerState.message, color = Color.Red)
            LaunchedEffect(Unit) {   Toast.makeText(context, registerState.message, Toast.LENGTH_LONG).show()
        }}

        else -> {
            Text("Register", fontSize = btnFont, color = Color.White)
        }
    }
}
