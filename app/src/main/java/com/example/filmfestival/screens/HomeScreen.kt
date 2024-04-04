package com.example.filmfestival.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.filmfestival.MainViewModel
import com.example.filmfestival.R
import com.example.filmfestival.composables.BottomNavBar
import com.example.filmfestival.ui.theme.LightBackground
import com.example.filmfestival.ui.theme.WhiteText


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel
){
    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ){
    paddingValues ->
        Column (
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App logo",
                modifier = Modifier
                    .padding(
                        start = 24.dp,
                        top = 36.dp,
                        end = 24.dp,
                        bottom = 36.dp
                    )
                    .clip(RoundedCornerShape(16.dp))
            )
//        Spacer(
//            modifier = Modifier
//                .height(24.dp)
//                .fillMaxWidth()
//        )
            Text(
                text = "News",
                fontSize = 36.sp,
                color = WhiteText
            )
            Divider(
                color = LightBackground,
                thickness = 2.dp,
                modifier = Modifier
                    .padding(
                        start = 24.dp,
                        end = 24.dp
                    )
            )
        }
    }
}