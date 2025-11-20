package com.example.notex.settings.data.models

import com.example.notex.settings.theme.AppColor
import com.example.notex.settings.theme.ThemeType

data class SettingsPrefModel(
    val theme: ThemeType,
    val language: String,
    val fontColor: AppColor,
    val backgroundColor: AppColor,
    val isAutoBackup: Boolean,
    val isDeletePer: Boolean
)