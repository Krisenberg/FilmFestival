package com.example.filmfestival.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.filmfestival.MainViewModel
import com.example.filmfestival.R
import com.example.filmfestival.composables.BottomNavBar
import com.example.filmfestival.utils.NavigationHelper

@Composable
fun NewsScreen(
    navHelper: NavigationHelper,
    viewModel: MainViewModel,
    imageRes: Int,
    date: String,
    text: String,
    description: String
) {
    Scaffold(
        bottomBar = { BottomNavBar(navHelper = navHelper) },
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    androidx.compose.material3.IconButton(
                        onClick = { navHelper.goBack() },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back arrow",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        }
    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            Image(
//                painter = painterResource(id = R.drawable.logo),
//                contentDescription = "App logo",
//                modifier = Modifier
//                    .padding(
//                        start = 24.dp,
//                        top = 36.dp,
//                        end = 24.dp,
//                        bottom = 36.dp
//                    )
//                    .clip(RoundedCornerShape(16.dp))
////            )
//            Spacer(modifier = Modifier.height(4.dp))
            Divider(
                color = MaterialTheme.colorScheme.background,
                thickness = 2.dp,
                modifier = Modifier
                    .padding(
                        start = 24.dp,
                        end = 24.dp
                    )
            )
            Image(
                painter = painterResource(imageRes),
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .width(350.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
            )
            Divider(
                color = MaterialTheme.colorScheme.background,
                thickness = 0.67.dp,
                modifier = Modifier
                    .padding(
                        start = 24.dp,
                        end = 24.dp
                    )
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = text,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = description,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

//@Composable
//fun NewsScreen(
//    navHelper: NavigationHelper,
//    viewModel: MainViewModel,
//    imageRes: Int,
//    date: String,
//    text: String,
//    description: String
//){
//    Scaffold(
//        bottomBar = { BottomNavBar(navHelper = navHelper) }
//    ){
//        paddingValues ->
//        Column (
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues),
//            verticalArrangement = Arrangement.spacedBy(24.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ){
//            Text(
//                text = text,
//                fontSize = 24.sp,
//                color = WhiteText,
//                textAlign = TextAlign.Center,
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                text = description,
//                fontSize = 18.sp,
//                color = Color.Gray,
//                textAlign = TextAlign.Center,
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//    }
//}