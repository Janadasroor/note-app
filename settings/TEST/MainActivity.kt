package com.example.myapplication.settings.TEST

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.auth.viewmodel.SettingsViewModel
import com.example.myapplication.settings.settingmanager.SecureSettingDataManager
import com.example.myapplication.settings.theme.AppColor
import com.example.myapplication.settings.theme.ThemeType

class MainActivity : ComponentActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SettingsScreen(settingsViewModel)
        }
    }
}

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel) {
    // Observe the settings
    val settings by settingsViewModel.settings.collectAsState()

    // Display settings
    Column {
        Text("Theme: ${settings?.theme?.name}")
        Text("Language: ${settings?.language}")
        Text("Font Color: ${settings?.fontColor?.name}")
        Text("Background Color: ${settings?.backgroundColor?.name}")
        Text("Auto Backup: ${settings?.isAutoBackup}")
        Text("Delete on Permissions: ${settings?.isDeletePer}")

        // Buttons to change settings
        Button(onClick = { settingsViewModel.setTheme(ThemeType.DARK) }) {
            Text("Set Dark Theme")
        }
        Button(onClick = { settingsViewModel.setLanguage("Spanish") }) {
            Text("Set Language to Spanish")
        }
        Button(onClick = { settingsViewModel.setFontColor(AppColor.RED) }) {
            Text("Set Font Color to Red")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val settingsViewModel = SettingsViewModel(SecureSettingDataManager(LocalContext.current)) // Just for preview
    SettingsScreen(settingsViewModel)
}
