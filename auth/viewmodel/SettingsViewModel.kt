package com.example.myapplication.auth.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.settings.settingmanager.SecureSettingDataManager
import com.example.myapplication.settings.data.models.SettingsPrefModel
import com.example.myapplication.settings.theme.AppColor
import com.example.myapplication.settings.theme.ThemeType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsManager: SecureSettingDataManager
) : ViewModel() {

    // MutableStateFlow to hold the settings
    private val _settings = MutableStateFlow(
        SettingsPrefModel(
            theme = ThemeType.LIGHT,
            language = "English",
            fontColor = AppColor.WHITE,
            backgroundColor = AppColor.WHITE,
            isAutoBackup = true,
            isDeletePer = false
        )
    )

    init {
        viewModelScope.launch {
            settingsManager.settings.collect { currentSettings ->
                _settings.value = currentSettings
            }
        }
    }
    // Public StateFlow to observe changes in settings
    val settings: StateFlow<SettingsPrefModel> get() = _settings

    // Function to fetch and update settings
    fun loadSettings() {
        viewModelScope.launch {
            settingsManager.settings.collect { currentSettings ->
                _settings.value = currentSettings
            }
        }
    }

    // Functions to update individual settings
    fun setTheme(theme: ThemeType) {
        viewModelScope.launch {
            settingsManager.setTheme(theme)
            loadSettings() // Reload settings after update
        }
    }

    fun setLanguage(language: String) {
        viewModelScope.launch {
            settingsManager.setLanguage(language)
            loadSettings() // Reload settings after update
        }
    }

    fun setFontColor(fontColor: AppColor) {
        viewModelScope.launch {
            settingsManager.setFontColor(fontColor)
            loadSettings() // Reload settings after update
        }
    }

    fun setBackgroundColor(backgroundColor: AppColor) {
        viewModelScope.launch {
            settingsManager.setBackgroundColor(backgroundColor)
            loadSettings() // Reload settings after update
        }
    }

    fun setAutoBackup(enabled: Boolean) {
        viewModelScope.launch {
            settingsManager.setAutoBackup(enabled)
            loadSettings() // Reload settings after update
        }
    }

    fun setDeletePer(enabled: Boolean) {
        viewModelScope.launch {
            settingsManager.setDeletePer(enabled)
            loadSettings() // Reload settings after update
        }
    }
}

class SettingsViewModelFactory(private val context: Context):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(SecureSettingDataManager(context = context)) as T
    }
}