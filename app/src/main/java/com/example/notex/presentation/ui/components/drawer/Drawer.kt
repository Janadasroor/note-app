package com.example.notex.presentation.ui.components.drawer

import android.annotation.SuppressLint
import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notex.R
import com.example.notex.auth.viewmodel.UserViewModel
import kotlinx.coroutines.launch



@Composable
fun DrawerContent(onClose: () -> Unit,userViewModel: UserViewModel,navController: NavController) {
    val userData by userViewModel.secureData.observeAsState()
    val email = userData?.email?:"your not login yet ."
    val isLogin by userViewModel.isLogin.collectAsState()
    val name = userData?.name?:"your not login yet ."
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(300.dp)
            .background(Color.White)
            .padding(2.dp)
    ) {
        // Menu Items
              // Push profile info to the bottom
        LazyColumn (
            modifier =Modifier
                .padding(top = 20.dp)
                .fillMaxHeight(0.86f)
        ){
            items(15)
            {

                Card (
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF5F2F2)
                    )
                    ,
                    shape = CircleShape.copy(CornerSize(1.dp)),
                    modifier = Modifier

                        .padding(2.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                ){
                    Column {
                        Text("Home", fontSize = 15.sp, modifier = Modifier
                            .padding(start = 10.dp, top = 2.dp, bottom = 2.dp)
                            .clickable { onClose() })
                        Text("content here", fontSize = 13.sp,
                            color = Color.DarkGray,
                            modifier = Modifier
                                .padding(start = 10.dp, top = 2.dp, bottom = 2.dp)
                                .clickable { onClose() })

                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        // Divider
        HorizontalDivider()

        // Profile Section
        Button(
            contentPadding = PaddingValues(0.dp),
             modifier = Modifier.padding(bottom = 40.dp),
            shape = RectangleShape,
            onClick = {
             navController.navigate(route = "settings")
            }
        ){
            Row(
                modifier = Modifier
                    .height(70.dp)
                    .background(Color(0XFFFFFDDF))
                    .fillMaxWidth()
                    ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier.size(50.dp),
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.app_icon), // Replace with your image
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)

            }
        }
    }
}




