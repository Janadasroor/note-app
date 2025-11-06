package com.example.myapplication.auth.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp

val txtFldFont=12.sp
val sTxtFldFont=11.sp
val titleFont=21.sp
val sTitleFont=18.sp
val btnFont=14.sp

val alreadyFont=13.sp


class FontSServices(density: Density) {
    private fun fixedFontSize(pxValue: Float): TextUnit {
        return TextUnit(pxValue, TextUnitType.Unspecified) // Prevent system scaling
    }

    val txtFldFont = 13.sp
    val titleFont = fixedFontSize(20f)
    val btnFont = fixedFontSize(17f)
    val alreadyFont = fixedFontSize(14f)
    val AppTypography = Typography(
        bodyLarge = TextStyle(fontSize = fixedFontSize(16f)), // Default is 16.sp
        titleLarge = TextStyle(fontSize = fixedFontSize(24f)), // Default is 24.sp
        labelLarge = TextStyle(fontSize = fixedFontSize(14f))  // Default is 14.sp
    )
}

