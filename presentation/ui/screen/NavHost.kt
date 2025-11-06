package com.example.myapplication.presentation.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.auth.presentation.ui.LoginNavHost
import com.example.myapplication.auth.viewmodel.UserViewModel
import com.example.myapplication.presentation.ui.screen.add.AddCodeNoteScreen
import com.example.myapplication.presentation.ui.screen.add.MindMapNoteScreen
import com.example.myapplication.presentation.ui.screen.add.TaskManagementScreen
import com.example.myapplication.presentation.ui.screen.add.TextNoteScreen
import com.example.myapplication.presentation.ui.screen.edit.EditCodeNote
import com.example.myapplication.presentation.ui.screen.view.ReadCodeScreen
import com.example.myapplication.RoomDb.viewmodel.NoteViewModel
import com.example.myapplication.settings.SettingsScreen
import com.example.myapplication.settings.data.models.SettingsPrefModel
import com.example.myapplication.settings.datacontrols.DataControlsScreen
import com.example.myapplication.settings.personalization.PersonalizationScreen
import com.example.myapplication.settings.settingmanager.SecureSettingDataManager
import com.example.myapplication.settings.theme.AppColor
import com.example.myapplication.settings.theme.MyAppTheme
import com.example.myapplication.settings.theme.ThemeType

@Composable
//TODO Navigation  Host
fun AppNavHost(context:Context, userViewModel:UserViewModel,  viewModel: NoteViewModel) {
    val navController = rememberNavController()
   val settingViewModel2:com.example.myapplication.auth.viewmodel.SettingsViewModel=com.example.myapplication.auth.viewmodel.SettingsViewModel(
       SecureSettingDataManager(context)
   )
  val settings =settingViewModel2.settings.collectAsState()
    val isLogin by userViewModel.isLogin.collectAsState()
   var themeType = remember { mutableStateOf(settings.value.theme ) }
  LaunchedEffect(settings.value.theme) {
      themeType.value=settings.value.theme
  }
    NavHost(
        navController = navController, startDestination = if (isLogin) "home" else "auth") {
       composable("auth"){


               LoginNavHost(
                   userViewModel = userViewModel,
                   navHome = navController
               )

       }
        composable("home") {
            MyAppTheme(themeType = themeType) {
                HomeScreen(
                    viewModel = viewModel,
                    navController = navController,
                    userViewModel = userViewModel
                )

            }
        }
        composable("add") {

            MyAppTheme (themeType = themeType) {
                NoteAddScreen(viewModel = viewModel, navController = navController)
            }
        }
        composable("read") {
            MyAppTheme(themeType = themeType) {
                NoteReadScreen(viewModel = viewModel, navController = navController)
            }
        }
        composable("edit") {
            MyAppTheme(themeType = themeType) {
                NoteEditScreen(viewModel = viewModel, navController = navController)
            }
        }


        composable("addCode") {
            MyAppTheme(themeType = themeType) {
                AddCodeNoteScreen(
                    viewModel = viewModel, navController = navController,
                    onSave = {}
                )
            }
        }
        composable("addTask") {
            MyAppTheme(themeType = themeType) {
                TaskManagementScreen(
                    viewModel = viewModel, 
                    navController = navController,
                    onSaveNote = { note ->
                        // The saving is now handled directly in the TaskManagementScreen
                        // through the viewModel.addTaskNote() method
                    }
                )
            }
        }
        composable("addTextNote") {
            MyAppTheme(themeType = themeType) {
                TextNoteScreen(
                    viewModel = viewModel, 
                    navController = navController,
                    onSaveNote = { note ->
                        // The saving is handled directly in the TextNoteScreen
                        // through the viewModel.addTextNote() method
                    }
                )
            }
        }
        composable("readCodeNote") {
            MyAppTheme(themeType = themeType) {
                ReadCodeScreen(
                    viewModel = viewModel, navController = navController,

                    )
            }
        }
        composable("addMindMap") {
            MyAppTheme(themeType = themeType) {
                MindMapNoteScreen(
                    viewModel = viewModel, 
                    navController = navController,
                    onSave = { note ->
                        // The saving is handled directly in the MindMapNoteScreen
                        // through the viewModel.addMindMapNote() method
                    }
                )
            }
        }
        composable("editCodeNote") {
            MyAppTheme(themeType = themeType) {
                EditCodeNote(
                    viewModel = viewModel, navController = navController,

                    )
            }
        }

        navigation(startDestination = "settings/main", route = "settings"){
            composable ("settings/main") {
                MyAppTheme(themeType = themeType) {
                    SettingsScreen(
                        navController = navController,

                        onLogout = {}
                    )
                }
            }
            composable ("settings/person") {
                MyAppTheme(themeType = themeType) {
                    PersonalizationScreen(
                        settNavController = navController
                    )
                }
            }
            composable ("settings/datacontrols") {
                MyAppTheme(themeType = themeType) {
                    DataControlsScreen(
                        settNavController = navController
                    )
                }
            }
        }

    }
}
