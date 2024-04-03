package com.example.filmfestival.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.filmfestival.composables.BottomNavBar

@Composable
fun MovieSet(
    navController: NavController
){
    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ){ paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            Text(
                text = "MOVIES HERE",
                fontSize = 60.sp
            )
        }
    }
}