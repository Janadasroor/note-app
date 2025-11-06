package com.example.myapplication.settings.settingmanager

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.*
import com.example.myapplication.data.dataStore
import com.example.myapplication.settings.theme.AppColor
import com.example.myapplication.settings.data.models.SettingsPrefModel
import com.example.myapplication.settings.theme.ThemeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SecureSettingDataManager(context: Context) {
    val dataStore = context.dataStore

    // Keys for DataStore preferences
    private object SettingsKeys {
        val THEME = stringPreferencesKey("theme_mode")
        val LANGUAGE = stringPreferencesKey("language")
        val FONT_COLOR = stringPreferencesKey("font_color")
        val BACKGROUND_COLOR = stringPreferencesKey("background_color")
        val BACKUP_AUTO = booleanPreferencesKey("auto_backup")
        val DELETE_PER = booleanPreferencesKey("delete_per")
    }

    // Flow for getting individual settings
    val backupAuto: Flow<Boolean> = dataStore.data.map {
        it[SettingsKeys.BACKUP_AUTO] ?: true
    }

    val deletePer: Flow<Boolean> = dataStore.data.map {
        it[SettingsKeys.DELETE_PER] ?: false
    }

    val theme: Flow<ThemeType> = dataStore.data.map {
        ThemeType.valueOf(it[SettingsKeys.THEME] ?: ThemeType.LIGHT.name)
    }

    val language: Flow<String> = dataStore.data.map {
        it[SettingsKeys.LANGUAGE] ?: "English"
    }

    val fontColor: Flow<AppColor> = dataStore.data.map {
        AppColor.valueOf(it[SettingsKeys.FONT_COLOR] ?: AppColor.WHITE.name)
    }

    val backgroundColor: Flow<AppColor> = dataStore.data.map {
        AppColor.valueOf(it[SettingsKeys.BACKGROUND_COLOR] ?: AppColor.WHITE.name)
    }

    // Get all settings as a SettingsPrefModel
    val settings: Flow<SettingsPrefModel> = dataStore.data.map { preferences ->
        SettingsPrefModel(
            theme = ThemeType.valueOf(preferences[SettingsKeys.THEME] ?: ThemeType.LIGHT.name),
            language = preferences[SettingsKeys.LANGUAGE] ?: "English",
            fontColor = AppColor.valueOf(preferences[SettingsKeys.FONT_COLOR] ?: AppColor.WHITE.name),
            backgroundColor = AppColor.valueOf(preferences[SettingsKeys.BACKGROUND_COLOR] ?: AppColor.WHITE.name),
            isAutoBackup = preferences[SettingsKeys.BACKUP_AUTO] ?: true,
            isDeletePer = preferences[SettingsKeys.DELETE_PER] ?: false
        )
    }

    // Suspend function to set values
    suspend fun setTheme(theme: ThemeType) {
        dataStore.edit { preferences ->
            preferences[SettingsKeys.THEME] = theme.name
        }
        Log.d("Tag",dataStore.data.first()[SettingsKeys.THEME]?:"Empty,,,,")
        Log.d("Tag",dataStore.data.first()[SettingsKeys.THEME]?:"Empty,,,,")
        Log.d("Tag",dataStore.data.first()[SettingsKeys.THEME]?:"Empty,,,,")
        Log.d("Tag",dataStore.data.first()[SettingsKeys.THEME]?:"Empty,,,,")
        Log.d("Tag",dataStore.data.first()[SettingsKeys.THEME]?:"Empty,,,,")
    }

    suspend fun setFontColor(appColor: AppColor) {
        dataStore.edit { preferences ->
            preferences[SettingsKeys.FONT_COLOR] = appColor.name
        }
    }

    suspend fun setBackgroundColor(appColor: AppColor) {
        dataStore.edit { preferences ->
            preferences[SettingsKeys.BACKGROUND_COLOR] = appColor.name
        }
    }

    suspend fun setLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[SettingsKeys.LANGUAGE] = language
        }
    }

    suspend fun setAutoBackup(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[SettingsKeys.BACKUP_AUTO] = enabled
        }
    }

    suspend fun setDeletePer(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[SettingsKeys.DELETE_PER] = enabled
        }
    }
}
