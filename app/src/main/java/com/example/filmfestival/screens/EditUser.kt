package com.example.filmfestival.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.filmfestival.MainViewModel
import com.example.filmfestival.R
import com.example.filmfestival.composables.BottomNavBar
import com.example.filmfestival.utils.NavigationHelper

@Composable
fun EditUser(
    navHelper: NavigationHelper,
    viewModel: MainViewModel,
    username : String,
    modifier: Modifier = Modifier
){
    Scaffold(
        bottomBar = { BottomNavBar(navHelper = navHelper) }
    ){ paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Button(
                    onClick = {
//                        navController.previousBackStackEntry?.savedStateHandle
//                        navController.popBackStack()
                        navHelper.goBack()
                    },
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier
                            .size(28.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(130.dp))
            RoundImage(
                image = painterResource(id = R.drawable.timothe),
                modifier = Modifier
                    .size(200.dp)
                    .padding(5.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = username,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .wrapContentWidth()
            )
        }
    }
}
