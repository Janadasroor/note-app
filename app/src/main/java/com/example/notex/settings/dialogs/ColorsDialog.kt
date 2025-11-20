package com.example.notex.settings.dialogs
import androidx.compose.foundation.Canvas
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
import com.example.notex.settings.theme.AppColor


@Preview
@Composable
fun ColorPreview(){
    var selectedTextColor = remember { mutableStateOf(AppColor.RED) }
    var selectedBackgroundColor = remember { mutableStateOf(AppColor.RED) }
    ColorDialog(
        selectedTextColor = selectedTextColor,
        selectedBackgroundColor = selectedBackgroundColor,
        onDismiss = {}
    )
}
@Composable
fun ColorDialog(
    selectedTextColor: MutableState<AppColor>,
    selectedBackgroundColor: MutableState<AppColor>,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
        AlertItemTextH1("Customize Colors")
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
             AlertItemTextH2("Text Color")
               LazyColumn (
                   modifier = Modifier.height(200.dp)
               ){
                   items(AppColor.values()){
                       it->
                       ColorOption(
                           color = it,
                           currentSelection = selectedTextColor.value,
                           onColorSelected = { selectedTextColor.value = it }
                       )
                   }
               }
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                AlertItemTextH2("Background Color")
                LazyColumn (
                    modifier = Modifier.height(200.dp)
                ){
                    items(AppColor.values()){
                            it->
                        ColorOption(
                            color = it,
                            currentSelection = selectedBackgroundColor.value,
                            onColorSelected = { selectedBackgroundColor.value = it }
                        )
                    }
                }

            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Done", color = MaterialTheme.colorScheme.onBackground)
            }
        }
    )
}

@Composable
fun ColorOption(
    color: AppColor,
    currentSelection: AppColor,
    onColorSelected: (AppColor) -> Unit
) {
    val isSelected = color == currentSelection
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onColorSelected(color) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Canvas(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .height(20.dp)
                    .fillMaxWidth(0.1f)
            ) {
                drawCircle(color = color.color)
            }
          AlertItemText(color.displayName)
        }

        RadioButton(
            selected = isSelected,
            onClick = { onColorSelected(color) },
            colors = RadioButtonDefaults.colors(selectedColor = color.color)
        )
    }
}
