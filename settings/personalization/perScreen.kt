package com.example.myapplication.settings.personalization


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.auth.viewmodel.SettingsViewModel
import com.example.myapplication.auth.viewmodel.SettingsViewModelFactory
import com.example.myapplication.settings.SettingPageH
import com.example.myapplication.settings.Settingsitem
import com.example.myapplication.settings.theme.AppColor
import com.example.myapplication.settings.theme.ThemeType
import com.example.myapplication.settings.dialogs.ColorDialog
import com.example.myapplication.settings.dialogs.LanguageDialog
import com.example.myapplication.settings.dialogs.ThemeDialog
import com.example.myapplication.settings.settingmanager.SecureSettingDataManager

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
            id = R.drawable.brightness,
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
          id = R.drawable.favorite,
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
     id = R.drawable.text_fields,
      name = "Font",
      onTap ={},
      contentDescription = "Set Font"
  )
    var currentLanguage= remember { mutableStateOf("English") }
    Settingsitem(
        id = R.drawable.language,
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
