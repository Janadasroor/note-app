package com.example.myapplication.settings


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.settings.theme.LocalExtraColors

@Composable
fun  SettingItemText(text:String){

    Text(text = text, color = Color.DarkGray, fontWeight = FontWeight.Bold, fontSize = 13.sp)

}
@Composable
fun SettingsCheckboxItem(
    label: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!isChecked) } // Toggle on row click
            .padding(horizontal = 8.dp, vertical = 12.dp), // Add vertical padding for better touch target
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 16.sp)
        Checkbox(
            checked = isChecked,
            onCheckedChange = null, // Prevent double toggle from both Checkbox & Row
            colors = CheckboxDefaults.colors(
                checkmarkColor = MaterialTheme.colorScheme.onPrimary, // Color of ✓
                checkedColor = MaterialTheme.colorScheme.primary,     // Checkbox fill when checked
                uncheckedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), // Outline color when unchecked
                disabledCheckedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                disabledUncheckedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
        )

    }
}
@Composable
fun  SettingPageH(text: String,onClick: () -> Unit){
    val extra= LocalExtraColors.current
    Row (
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(
            onClick = onClick,
            content ={
                Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "Back", tint = extra.deepText)

            }
        )
        Text(text, fontWeight = FontWeight.Bold, fontSize = 19.sp, color = extra.deepText)

    }
}
@Composable
fun Settingsitem(id:Int, name:String, onTap:() ->Unit,contentDescription:String) {
    val iconSize=25.dp
    val itemFontSize=16.sp
    Button  (
        onClick = onTap
        , contentPadding = PaddingValues(0.dp)
        , shape = RectangleShape
        , colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier
                .height(70.dp)

                .fillMaxWidth()
                .padding(end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
            , horizontalArrangement = Arrangement.SpaceBetween
        ) {
           Row (

               verticalAlignment = Alignment.CenterVertically
           ) {
                Icon(
                    painter = painterResource(id = id), // Replace with your image
                    // Replace with your image
                    contentDescription = contentDescription,

                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(iconSize)
                )


                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    name,
                    fontSize = itemFontSize,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Normal
                )
            }
            Icon(

                imageVector = Icons.Default.KeyboardArrowRight, // Replace with your image
                contentDescription = contentDescription,

                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(iconSize),

            )

        }
    }
}