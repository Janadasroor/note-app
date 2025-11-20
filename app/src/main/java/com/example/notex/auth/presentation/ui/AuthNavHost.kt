package com.example.notex.auth.presentation.ui


import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notex.auth.presentation.ui.login.LoginScreen
import com.example.notex.auth.presentation.ui.login.SmallLoginScreen
import com.example.notex.auth.presentation.ui.register.RegisterScreen
import com.example.notex.auth.presentation.ui.register.SmallRegisterScreen
import com.example.notex.auth.presentation.ui.register.VerificationScreen
import com.example.notex.auth.viewmodel.LoginVm
import com.example.notex.auth.viewmodel.LoginVmFact
//import com.example.notex.auth.viewmodel.AuthViewModel
import com.example.notex.auth.viewmodel.UserViewModel


@Composable

fun LoginNavHost(userViewModel: UserViewModel,navHome: NavController){
    val navController = rememberNavController()
    val config = LocalConfiguration.current
   val context= LocalContext.current
    val screenWidth = config.screenWidthDp
    val height = config.screenHeightDp
    val   loginVm: LoginVm = viewModel(factory = LoginVmFact(
        context = context
    ))
    NavHost(navController = navController, startDestination =when {
        screenWidth <390 -> "addsmall"
        else ->"add"
    }
    ) {
        composable("login") {
            LoginScreen(

                userViewModel = userViewModel,
                navController = navController,
                navHome = navHome,
                loginVm = loginVm
            )
        }
        composable("loginsmall") {
            SmallLoginScreen(

                userViewModel = userViewModel,
                navController = navController,
                navHome=navHome,
                loginVm = loginVm
            )
        }
        composable("add") {
            RegisterScreen(
                userViewModel=userViewModel,
                navHome = navHome,
//                registerViewModel = authViewModel,
                navController = navController
            )
        }
     composable("addsmall") {
            SmallRegisterScreen(
                userViewModel=userViewModel,
                navHome = navHome,
//                registerViewModel = authViewModel,
                navController = navController
            )
        }

         composable("verify") {
             VerificationScreen(
                 userViewModel=userViewModel,

                 navLogin =navController)
         }
            }
    }

