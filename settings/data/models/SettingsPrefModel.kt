package com.example.myapplication.settings.data.models

import com.example.myapplication.settings.theme.AppColor
import com.example.myapplication.settings.theme.ThemeType

data class SettingsPrefModel(
    val theme: ThemeType,
    val language: String,
    val fontColor: AppColor,
    val backgroundColor: AppColor,
    val isAutoBackup: Boolean,
    val isDeletePer: Boolean
)