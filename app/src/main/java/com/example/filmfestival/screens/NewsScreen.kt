package com.example.filmfestival.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.filmfestival.MainViewModel
import com.example.filmfestival.composables.BottomNavBar
import com.example.filmfestival.ui.theme.WhiteText
import com.example.filmfestival.utils.NavigationHelper

@Composable
fun NewsScreen(
    navHelper: NavigationHelper,
    viewModel: MainViewModel,
    news: News
){
    Scaffold(
        bottomBar = { BottomNavBar(navHelper = navHelper) }
    ){
        paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            Text(
                text = news.text,
                fontSize = 24.sp,
                color = WhiteText,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = news.description,
                fontSize = 18.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}