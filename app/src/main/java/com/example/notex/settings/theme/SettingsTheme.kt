package com.example.notex.settings.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape

// ui/theme/Theme.kt


//region colors scheme
// Default Light Theme
val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE),
    onPrimary = Color.White,
    secondary = Color.Black,
    onSecondary = Color.Black,
    background = Color(0xFFF0F0F0),
    onBackground = Color.Black,
    surface = Color(0xFFF5F5F5),
    onSurface = Color.Black
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC),
    onPrimary = Color.Black,
    secondary = Color(0xFF03DAC6),
    onSecondary = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White
)

val PinkDarkColorScheme = darkColorScheme(
    primary = Color(0xFFAD1457),
    onPrimary = Color.White,
    secondary = Color(0xFFD81B60),
    onSecondary = Color.White,
    background = Color(0xFF3E1D2D),
    onBackground = Color.White,
    surface = Color(0xFF4A273B),
    onSurface = Color.White
)

val BlueDarkColorScheme = darkColorScheme(
    primary = Color(0xFF1976D2),
    onPrimary = Color.White,
    secondary = Color(0xFF64B5F6),
    onSecondary = Color.White,
    background = Color(0xFF102027),
    onBackground = Color.White,
    surface = Color(0xFF1A237E),
    onSurface = Color.White
)

val GreenDarkColorScheme = darkColorScheme(
    primary = Color(0xFF388E3C),
    onPrimary = Color.White,
    secondary = Color(0xFF66BB6A),
    onSecondary = Color.White,
    background = Color(0xFF1B5E20),
    onBackground = Color.White,
    surface = Color(0xFF2E7D32),
    onSurface = Color.White
)
val HighContrastLightColorScheme = lightColorScheme(
    primary = Color.Black,
    onPrimary = Color.White,
    secondary = Color.DarkGray,
    onSecondary = Color.White,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.LightGray,
    onSurface = Color.Black
)
val HighContrastDarkColorScheme = darkColorScheme(
    primary = Color.White,
    onPrimary = Color.Black,
    secondary = Color.LightGray,
    onSecondary = Color.Black,
    background = Color.Black,
    onBackground = Color.White,
    surface = Color.DarkGray,
    onSurface = Color.White
)


// Pink Theme
val PinkColorScheme = lightColorScheme(
    primary = Color(0xFFE91E63),
    onPrimary = Color.White,
    secondary = Color(0xFFF06292),
    onSecondary = Color.Black,
    background = Color(0xFFFCE4EC),
    onBackground = Color.Black,
    surface = Color(0xFFF8BBD0),
    onSurface = Color.Black
)

// Blue Theme
val BlueColorScheme = lightColorScheme(
    primary = Color(0xFF2196F3),
    onPrimary = Color.White,
    secondary = Color(0xFF64B5F6),
    onSecondary = Color.Black,
    background = Color(0xFFE3F2FD),
    onBackground = Color.Black,
    surface = Color(0xFFBBDEFB),
    onSurface = Color.Black
)

// Green Theme
val GreenColorScheme = lightColorScheme(
    primary = Color(0xFF4CAF50),
    onPrimary = Color.White,
    secondary = Color(0xFF81C784),
    onSecondary = Color.Black,
    background = Color(0xFFE8F5E9),
    onBackground = Color.Black,
    surface = Color(0xFFC8E6C9),
    onSurface = Color.Black
)


val ForestDarkColorScheme = darkColorScheme(
    primary = Color(0xFF2E7D32),
    onPrimary = Color.White,
    secondary = Color(0xFF81C784),
    onSecondary = Color.Black,
    background = Color(0xFF1B5E20),
    onBackground = Color.White,
    surface = Color(0xFF2C2C2C),
    onSurface = Color.White
)
val PurpleLightColorScheme = lightColorScheme(
    primary = Color(0xFF9C27B0),
    onPrimary = Color.White,
    secondary = Color(0xFFCE93D8),
    onSecondary = Color.Black,
    background = Color(0xFFF3E5F5),
    onBackground = Color.Black,
    surface = Color(0xFFE1BEE7),
    onSurface = Color.Black
)
val PurpleDarkColorScheme = darkColorScheme(
    primary = Color(0xFFBA68C8),
    onPrimary = Color.Black,
    secondary = Color(0xFF8E24AA),
    onSecondary = Color.White,
    background = Color(0xFF311B92),
    onBackground = Color.White,
    surface = Color(0xFF4A148C),
    onSurface = Color.White
)
val OrangeLightColorScheme = lightColorScheme(
    primary = Color(0xFFFF9800),
    onPrimary = Color.Black,
    secondary = Color(0xFFFFCC80),
    onSecondary = Color.Black,
    background = Color(0xFFFFF3E0),
    onBackground = Color.Black,
    surface = Color(0xFFFFE0B2),
    onSurface = Color.Black
)
val OrangeDarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFB74D),
    onPrimary = Color.Black,
    secondary = Color(0xFFF57C00),
    onSecondary = Color.White,
    background = Color(0xFF3E2723),
    onBackground = Color.White,
    surface = Color(0xFF5D4037),
    onSurface = Color.White
)
val RedLightColorScheme = lightColorScheme(
    primary = Color(0xFFF44336),
    onPrimary = Color.White,
    secondary = Color(0xFFEF9A9A),
    onSecondary = Color.Black,
    background = Color(0xFFFFEBEE),
    onBackground = Color.Black,
    surface = Color(0xFFFFCDD2),
    onSurface = Color.Black
)
val RedDarkColorScheme = darkColorScheme(
    primary = Color(0xFFE57373),
    onPrimary = Color.Black,
    secondary = Color(0xFFD32F2F),
    onSecondary = Color.White,
    background = Color(0xFFB71C1C),
    onBackground = Color.White,
    surface = Color(0xFF880E4F),
    onSurface = Color.White
)
val YellowLightColorScheme = lightColorScheme(
    primary = Color(0xFFFFEB3B),
    onPrimary = Color.Black,
    secondary = Color(0xFFFFF176),
    onSecondary = Color.Black,
    background = Color(0xFFFFFDE7),
    onBackground = Color.Black,
    surface = Color(0xFFFFF9C4),
    onSurface = Color.Black
)
val YellowDarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFF176),
    onPrimary = Color.Black,
    secondary = Color(0xFFFBC02D),
    onSecondary = Color.Black,
    background = Color(0xFFF57F17),
    onBackground = Color.White,
    surface = Color(0xFFFBC02D),
    onSurface = Color.Black
)

val ForestLightColorScheme = lightColorScheme(
    primary = Color(0xFF81C784),
    onPrimary = Color.Black,
    secondary = Color(0xFF388E3C),
    onSecondary = Color.White,
    background = Color(0xFFE8F5E9),
    onBackground = Color.Black,
    surface = Color(0xFFF1F8E9),
    onSurface = Color.Black
)
val OceanDarkColorScheme = darkColorScheme(
    primary = Color(0xFF0288D1),
    onPrimary = Color.White,
    secondary = Color(0xFF26C6DA),
    onSecondary = Color.Black,
    background = Color(0xFF01579B),
    onBackground = Color.White,
    surface = Color(0xFF263238),
    onSurface = Color.White
)

val OceanLightColorScheme = lightColorScheme(
    primary = Color(0xFF4FC3F7),
    onPrimary = Color.Black,
    secondary = Color(0xFF0288D1),
    onSecondary = Color.White,
    background = Color(0xFFE1F5FE),
    onBackground = Color.Black,
    surface = Color(0xFFE0F7FA),
    onSurface = Color.Black
)
val SunsetDarkColorScheme = darkColorScheme(
    primary = Color(0xFFFF7043),
    onPrimary = Color.Black,
    secondary = Color(0xFFFFA726),
    onSecondary = Color.Black,
    background = Color(0xFF4E342E),
    onBackground = Color.White,
    surface = Color(0xFF3E2723),
    onSurface = Color.White
)

val SunsetLightColorScheme = lightColorScheme(
    primary = Color(0xFFFFA726),
    onPrimary = Color.Black,
    secondary = Color(0xFFFF7043),
    onSecondary = Color.White,
    background = Color(0xFFFFF3E0),
    onBackground = Color.Black,
    surface = Color(0xFFFFE0B2),
    onSurface = Color.Black
)

val MatrixDarkColorScheme = darkColorScheme(
    primary = Color(0xFF00FF00),
    onPrimary = Color.Black,
    secondary = Color(0xFF00AA00),
    onSecondary = Color.Black,
    background = Color(0xFF000000),
    onBackground = Color(0xFF00FF00),
    surface = Color(0xFF1B1B1B),
    onSurface = Color(0xFF00FF00)
)

val RetroDarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFC107),
    onPrimary = Color.Black,
    secondary = Color(0xFFFF5722),
    onSecondary = Color.Black,
    background = Color(0xFF3E2723),
    onBackground = Color.White,
    surface = Color(0xFF5D4037),
    onSurface = Color.White
)

val RetroLightColorScheme = lightColorScheme(
    primary = Color(0xFFFFD54F),
    onPrimary = Color.Black,
    secondary = Color(0xFFFF8A65),
    onSecondary = Color.Black,
    background = Color(0xFFFFF8E1),
    onBackground = Color.Black,
    surface = Color(0xFFFFECB3),
    onSurface = Color.Black
)
val NeonDarkColorScheme = darkColorScheme(
    primary = Color(0xFF00E5FF),
    onPrimary = Color.Black,
    secondary = Color(0xFF69F0AE),
    onSecondary = Color.Black,
    background = Color(0xFF0D0D0D),
    onBackground = Color.White,
    surface = Color(0xFF1A1A1A),
    onSurface = Color.White
)
// Matrix Theme
val MatrixColorScheme = darkColorScheme(
    primary = Color(0xFF00FF00),
    onPrimary = Color.Black,
    secondary = Color(0xFF00AA00),
    onSecondary = Color.Black,
    background = Color(0xFF000000),
    onBackground = Color(0xFF00FF00),
    surface = Color(0xFF1B1B1B),
    onSurface = Color(0xFF00FF00)
)

// Retro Theme
val RetroColorScheme = lightColorScheme(
    primary = Color(0xFFFFD54F),
    onPrimary = Color.Black,
    secondary = Color(0xFFFF8A65),
    onSecondary = Color.Black,
    background = Color(0xFFFFF8E1),
    onBackground = Color.Black,
    surface = Color(0xFFFFECB3),
    onSurface = Color.Black
)

val NeonColorScheme = darkColorScheme(
    primary = Color(0xFF00E5FF), // Neon Blue
    onPrimary = Color.Black, // Contrast for neon blue
    secondary = Color(0xFF69F0AE), // Neon Green
    onSecondary = Color.Black, // Contrast for neon green
    background = Color(0xFF121212), // Dark background
    onBackground = Color.White, // Text on dark background
    surface = Color(0xFF1A1A1A), // Dark surface
    onSurface = Color.White // Text on dark surface
)



//endregion


// Neon Typography Tokens
val NeonTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        color = Color(0xFF00E5FF), // Neon Blue Glow
        shadow = Shadow(
            color = Color(0xFF00E5FF),
            offset = Offset(2f, 2f),
            blurRadius = 8f
        )
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        color = Color(0xFF00E5FF), // Neon Blue Glow
        shadow = Shadow(
            color = Color(0xFF00E5FF),
            offset = Offset(2f, 2f),
            blurRadius = 8f
        )
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        color = Color(0xFF00E5FF), // Neon Blue Glow
        shadow = Shadow(
            color = Color(0xFF00E5FF),
            offset = Offset(2f, 2f),
            blurRadius = 8f
        )
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = Color(0xFF00E5FF), // Neon Blue Glow
        shadow = Shadow(
            color = Color(0xFF00E5FF),
            offset = Offset(2f, 2f),
            blurRadius = 8f
        )
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = Color(0xFF00E5FF), // Neon Blue Glow
        shadow = Shadow(
            color = Color(0xFF00E5FF),
            offset = Offset(2f, 2f),
            blurRadius = 8f
        )
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = Color(0xFF00E5FF), // Neon Blue Glow
        shadow = Shadow(
            color = Color(0xFF00E5FF),
            offset = Offset(2f, 2f),
            blurRadius = 8f
        )
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = Color(0xFF00E5FF), // Neon Blue Glow
        shadow = Shadow(
            color = Color(0xFF00E5FF),
            offset = Offset(2f, 2f),
            blurRadius = 8f
        )
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        color = Color(0xFF00E5FF), // Neon Blue Glow
        shadow = Shadow(
            color = Color(0xFF00E5FF),
            offset = Offset(2f, 2f),
            blurRadius = 6f
        )
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Color(0xFF00E5FF), // Neon Blue Glow
        shadow = Shadow(
            color = Color(0xFF00E5FF),
            offset = Offset(2f, 2f),
            blurRadius = 6f
        )
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        color = Color.White, // Default text color
        shadow = Shadow(
            color = Color(0xFF69F0AE), // Neon Green Glow
            offset = Offset(1f, 1f),
            blurRadius = 6f
        )
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Color.White, // Default text color
        shadow = Shadow(
            color = Color(0xFF69F0AE), // Neon Green Glow
            offset = Offset(1f, 1f),
            blurRadius = 6f
        )
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = Color.White, // Default text color
        shadow = Shadow(
            color = Color(0xFF69F0AE), // Neon Green Glow
            offset = Offset(1f, 1f),
            blurRadius = 6f
        )
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = Color(0xFF00E5FF), // Neon Blue Glow
        shadow = Shadow(
            color = Color(0xFF00E5FF),
            offset = Offset(1f, 1f),
            blurRadius = 4f
        )
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = Color(0xFF00E5FF), // Neon Blue Glow
        shadow = Shadow(
            color = Color(0xFF00E5FF),
            offset = Offset(1f, 1f),
            blurRadius = 4f
        )
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        color = Color(0xFF00E5FF), // Neon Blue Glow
        shadow = Shadow(
            color = Color(0xFF00E5FF),
            offset = Offset(1f, 1f),
            blurRadius = 4f
        )
    )
)


@Composable
fun MyAppTheme(
    themeType: MutableState<ThemeType> = mutableStateOf(ThemeType.LIGHT),
    content: @Composable () -> Unit
) {

    val colorScheme = when (themeType.value) {
        ThemeType.LIGHT -> LightColorScheme
        ThemeType.DARK -> DarkColorScheme
        ThemeType.PINK_LIGHT -> PinkColorScheme
        ThemeType.PINK_DARK -> PinkDarkColorScheme
        ThemeType.BLUE_LIGHT -> BlueColorScheme
        ThemeType.BLUE_DARK -> BlueDarkColorScheme
        ThemeType.GREEN_LIGHT -> GreenColorScheme
        ThemeType.GREEN_DARK -> GreenDarkColorScheme
        ThemeType.HIGH_CONTRAST_LIGHT -> HighContrastLightColorScheme
        ThemeType.HIGH_CONTRAST_DARK -> HighContrastDarkColorScheme
        ThemeType.MATRIX_DARK -> MatrixColorScheme
        ThemeType.RETRO_DARK -> RetroColorScheme
        ThemeType.NEON_DARK -> NeonColorScheme
        ThemeType.OCEAN_DARK -> OceanDarkColorScheme
        ThemeType.FOREST_DARK -> ForestDarkColorScheme
        ThemeType.SUNSET_DARK -> SunsetDarkColorScheme
        ThemeType.PURPLE_LIGHT -> PurpleLightColorScheme
        ThemeType.PURPLE_DARK -> PurpleDarkColorScheme
        ThemeType.ORANGE_LIGHT -> OrangeLightColorScheme
        ThemeType.ORANGE_DARK -> OrangeDarkColorScheme
        ThemeType.RED_LIGHT -> RedLightColorScheme
        ThemeType.RED_DARK -> RedDarkColorScheme
        ThemeType.YELLOW_LIGHT -> YellowLightColorScheme
        ThemeType.YELLOW_DARK -> YellowDarkColorScheme
    }


    val extraColors = if (themeType.value.name.contains("DARK")) {
        ExtraColors(
            warning = Color(0xFFFFB74D),  // Brighter warning color for dark themes
            success = Color(0xFF66BB6A),  // Brighter success color for dark themes
            info = Color(0xFF4FC3F7),     // Brighter info color for dark themes
            deepText = Color.White
        )
    } else {
        ExtraColors(
            warning = Color(0xFFF57C00),  // Deeper warning color for light themes
            success = Color(0xFF388E3C),  // Deeper success color for light themes
            info = Color(0xFF0288D1),     // Deeper info color for light themes
            deepText = Color.Black
        )
    }
    
    // Choose typography based on theme type
    val typography = if (themeType.value == ThemeType.NEON_DARK || 
                         themeType.value == ThemeType.MATRIX_DARK) {
        NeonTypography
    } else {
        Typography(
            // Default typography with improved readability
            titleLarge = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                letterSpacing = 0.sp,
                lineHeight = 28.sp
            ),
            titleMedium = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                letterSpacing = 0.15.sp,
                lineHeight = 24.sp
            ),
            titleSmall = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                letterSpacing = 0.1.sp,
                lineHeight = 20.sp
            ),
            bodyLarge = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                letterSpacing = 0.5.sp,
                lineHeight = 24.sp
            ),
            bodyMedium = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                letterSpacing = 0.25.sp,
                lineHeight = 20.sp
            ),
            bodySmall = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                letterSpacing = 0.4.sp,
                lineHeight = 16.sp
            ),
            labelLarge = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                letterSpacing = 0.1.sp,
                lineHeight = 20.sp
            ),
            labelMedium = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                letterSpacing = 0.5.sp,
                lineHeight = 16.sp
            ),
            labelSmall = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                letterSpacing = 0.5.sp,
                lineHeight = 16.sp
            )
        )
    }
    
    CompositionLocalProvider(LocalExtraColors provides extraColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            shapes = Shapes(
                small = RoundedCornerShape(4.dp),
                medium = RoundedCornerShape(8.dp),
                large = RoundedCornerShape(16.dp)
            )
        ) {
            Surface(
                color = MaterialTheme.colorScheme.background,
                content = content
            )
        }
    }
}
