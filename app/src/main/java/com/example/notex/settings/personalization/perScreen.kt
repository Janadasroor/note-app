package com.example.notex.settings.personalization


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness5
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.notex.R
import com.example.notex.auth.viewmodel.SettingsViewModel
import com.example.notex.auth.viewmodel.SettingsViewModelFactory
import com.example.notex.settings.SettingPageH
import com.example.notex.settings.Settingsitem
import com.example.notex.settings.dialogs.ColorDialog
import com.example.notex.settings.dialogs.LanguageDialog
import com.example.notex.settings.dialogs.ThemeDialog
import com.example.notex.settings.settingmanager.SecureSettingDataManager

@Composable
fun PersonalizationScreen(
    settNavController: NavController,
    settingsViewModel: SettingsViewModel=SettingsViewModel(SecureSettingDataManager(LocalContext.current))
    //viewModel: SettingsViewModel,

) {
    val context = LocalContext.current
//    val isSyncEnabled by viewModel.isSyncEnabled.collectAsState()
//    val user by viewModel.user.collectAsState()
//    val selectedTheme by viewModel.selectedTheme.collectAsState()
   val iconSize=25.dp
    val itemFontSize=16.sp
    settingsViewModel.loadSettings()
    val settings= settingsViewModel.settings.collectAsState()
   var languageDialog by remember { mutableStateOf(false) }
   var themeDialog by remember { mutableStateOf(false) }
   var colorDialog by remember { mutableStateOf(false) }
    var currentTheme = remember { mutableStateOf(settings.value.theme) }

    var selectedTextColor = remember { mutableStateOf(settings.value.fontColor) }
    var selectedBackgroundColor = remember { mutableStateOf(settings.value.backgroundColor) }

    LaunchedEffect(settings.value.theme) {
      //  viewModel.loadUser(context)
        currentTheme.value=settings.value.theme
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp, start = 10.dp)
        , verticalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        SettingPageH("Personalization", onClick = {settNavController.popBackStack()})


        Settingsitem(
            icon = Icons.Filled.Brightness5,
            name = "Theme",
            onTap = {
                themeDialog=true
                settingsViewModel.loadSettings()

                Log.e("Tag",settings.value.theme.name)
                Log.e("Tag",settings.value.theme.name)
                Log.e("Tag",settings.value.theme.name)
                Log.e("Tag",settings.value.theme.name)
                Log.e("Tag",settings.value.theme.name)
                    },
            contentDescription = "Set Theme"
        )
        if (themeDialog){
            ThemeDialog(
                currentTheme = currentTheme,
                onDismiss = {
                    themeDialog=false
                    settingsViewModel.setTheme(currentTheme.value)
                },
                onThemeSelected = { theme ->
                    currentTheme.value = theme
                }
            )
        }

      Settingsitem(
          icon = Icons.Filled.Favorite,
          name = "Color",
          onTap = {
              colorDialog=true
          },
          contentDescription = "Set Color"
      )

        if(colorDialog){
        ColorDialog(
            selectedTextColor = selectedTextColor,
            selectedBackgroundColor = selectedBackgroundColor,
            onDismiss = {
                colorDialog=false
                settingsViewModel.setFontColor(selectedTextColor.value)
                settingsViewModel.setFontColor(selectedBackgroundColor.value)

            }
        )
    }
  Settingsitem(
     icon = Icons.Filled.TextFields,
      name = "Font",
      onTap ={},
      contentDescription = "Set Font"
  )
    var currentLanguage= remember { mutableStateOf("English") }
    Settingsitem(
        icon = Icons.Filled.Language,
        name = "Language",
        onTap = {languageDialog=true},
        contentDescription = "App Language"
    )
   if(languageDialog){
       LanguageDialog(
           currentLanguage = currentLanguage,
           onDismiss = {languageDialog=false}
       ) {
           l->
           currentLanguage.value=l
       }
   }
   }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    val context = LocalContext.current
   val settingsViewModel: SettingsViewModel = viewModel(factory = SettingsViewModelFactory(context))

    PersonalizationScreen (
       settingsViewModel = settingsViewModel,

        settNavController = NavController(context)
    )
}
