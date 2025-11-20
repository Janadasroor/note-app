package com.example.notex.settings.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


enum class ThemeType(val displayName: String) {
        LIGHT("Light"),
        DARK("Dark"),

        PINK_LIGHT("Pink Light"),
        PINK_DARK("Pink Dark"),

        BLUE_LIGHT("Blue Light"),
        BLUE_DARK("Blue Dark"),

        GREEN_LIGHT("Green Light"),
        GREEN_DARK("Green Dark"),

        HIGH_CONTRAST_LIGHT("High Contrast Light"),
        HIGH_CONTRAST_DARK("High Contrast Dark"),
        MATRIX_DARK("Matrix"),
        FOREST_DARK("Forest"),
        SUNSET_DARK("Sunset"),
        RETRO_DARK("Retro"),
        NEON_DARK("Neon"),
        OCEAN_DARK("Ocean"),

        PURPLE_LIGHT("Purple Light"),
        PURPLE_DARK("Purple Dark"),

        ORANGE_LIGHT("Orange Light"),
        ORANGE_DARK("Orange Dark"),

        RED_LIGHT("Red Light"),
        RED_DARK("Red Dark"),

        YELLOW_LIGHT("Yellow Light"),
        YELLOW_DARK("Yellow Dark"),


}


enum class AppColor(val displayName: String, val color: Color) {
        BLACK("Black", Color.Black),
        WHITE("White", Color.White),
        RED("Red", Color.Red),
        GREEN("Green", Color.Green),
        BLUE("Blue", Color.Blue),
        YELLOW("Yellow", Color.Yellow)
}


data class ExtraColors(
        val warning: Color = Color.Unspecified,
        val success: Color = Color.Unspecified,
        val info: Color = Color.Unspecified,
        val deepText: Color =Color.Unspecified
)


val LocalExtraColors = staticCompositionLocalOf { ExtraColors() }