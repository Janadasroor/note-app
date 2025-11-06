package com.example.myapplication.settings.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
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

@Preview
@Composable

fun LanguagePreview(){
    var current = remember { mutableStateOf("English") }

    LanguageDialog(
        currentLanguage = current,
        onDismiss = {},
        onLanguageSelected = {}
    )
}
@Composable
fun LanguageDialog(
    currentLanguage: MutableState<String>,
    onDismiss: () -> Unit,
    onLanguageSelected: (String) -> Unit
) {
    AlertDialog(

        onDismissRequest = onDismiss,
        title = {
            AlertItemTextH1("App Language")
        },

        text = {
            Column {
                Divider(modifier = Modifier.height(1.dp))
                LanguageOption(
                    language = "English",
                    currentLanguage = currentLanguage.value,
                    onLanguageSelected = onLanguageSelected
                )
                LanguageOption(
                    language = "española",
                    currentLanguage = currentLanguage.value,
                    onLanguageSelected = onLanguageSelected
                )
                LanguageOption(
                    language = "العربية",
                    currentLanguage = currentLanguage.value,
                    onLanguageSelected = onLanguageSelected
                )
                  LanguageOption(
                    language = "Deutsch",
                    currentLanguage = currentLanguage.value,
                    onLanguageSelected = onLanguageSelected
                )
                LanguageOption(
                    language = "Русский",
                    currentLanguage = currentLanguage.value,
                    onLanguageSelected = onLanguageSelected
                )
                Divider(modifier = Modifier.height(1.dp))

            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Done", color = MaterialTheme.colorScheme.onBackground,)
            }
        }
    )
}

@Composable
fun LanguageOption(
    language: String,
    currentLanguage: String,
    onLanguageSelected: (String) -> Unit
) {
    val isSelected = language == currentLanguage
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onLanguageSelected(language) },
        horizontalArrangement = Arrangement.SpaceBetween
        , verticalAlignment = Alignment.CenterVertically
    ) {
        AlertItemText(language)
        RadioButton(
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.secondary
                , unselectedColor =  MaterialTheme.colorScheme.secondary

            ),
            selected = isSelected,
            onClick = {
              onLanguageSelected(language)
            })
        if (isSelected) {

        }
    }
}
