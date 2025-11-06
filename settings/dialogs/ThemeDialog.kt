package com.example.myapplication.settings.dialogs


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.settings.theme.ThemeType
import org.bouncycastle.math.raw.Mod

@Preview
@Composable

fun ThemePreview(){

    var currentTheme = remember { mutableStateOf(ThemeType.LIGHT) }

    ThemeDialog(
        currentTheme = currentTheme,
        onDismiss = {},
        onThemeSelected = {}
    )
}
@Composable
fun ThemeDialog(
    currentTheme: MutableState<ThemeType>,
    onDismiss: () -> Unit,
    onThemeSelected: (ThemeType) -> Unit
) {
    AlertDialog(

        onDismissRequest = onDismiss,
        title = {
           AlertItemTextH1("App Theme")
                },
        text = {
            LazyColumn (
                modifier = Modifier.
                    height(200.dp)

            ) {
                items(ThemeType.values()){
                   it-> ThemeOption(
                        theme = it,
                        currentTheme = currentTheme.value,
                        onThemeSelected = onThemeSelected
                    )
                }



            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Done",  color = MaterialTheme.colorScheme.onBackground,)
            }
        }
    )
}

@Composable
fun ThemeOption(
    theme: ThemeType,
    currentTheme: ThemeType,
    onThemeSelected: (ThemeType) -> Unit
) {
    val isSelected = theme == currentTheme
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onThemeSelected(theme) },
        horizontalArrangement = Arrangement.SpaceBetween
        , verticalAlignment = Alignment.CenterVertically
    ) {
       AlertItemText(theme.displayName)
        RadioButton(
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.secondary
                , unselectedColor =  MaterialTheme.colorScheme.secondary
            ),
            selected = isSelected,
            onClick = {
                onThemeSelected(theme)
            })
        if (isSelected) {

        }
    }
}
