package com.example.notex.settings//package com.example.notex.presentation.ui.screen.settings
//
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.platform.LocalContext
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import com.example.notex.presentation.ui.screen.settings.personalization.PersonalizationScreen
//
//@Composable
//fun SettingsNavHost(navController: NavController){
//    val context= LocalContext.current
//    val settingsViewModel:SettingsViewModel= viewModel(factory = SettingsViewModelFactory(context = context))
//    val settNavController= rememberNavController()
//    NavHost(
//       navController = settNavController,
//        startDestination = "main"
//
//    ){
//        composable("main") {
//            SettingsScreen(
//                navController = navController,
//                settNavController = settNavController,
//                viewModel = settingsViewModel
//            ) { }
//        }
//        composable("person") {
//            PersonalizationScreen (
//                settNavController = settNavController,
//
//            ) { }
//        }
//    }
//}