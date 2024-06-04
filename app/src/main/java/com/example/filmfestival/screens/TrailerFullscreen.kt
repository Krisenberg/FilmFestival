//package com.example.filmfestival.screens
//
//import android.view.View
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.Icon
//import androidx.compose.material.IconButton
//import androidx.compose.material.Scaffold
//import androidx.compose.material.Surface
//import androidx.compose.material.TabRowDefaults.Divider
//import androidx.compose.material.Text
//import androidx.compose.material.TopAppBar
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.navigation.NavController
//import com.example.filmfestival.MainViewModel
//import com.example.filmfestival.R
//import com.example.filmfestival.composables.BottomNavBar
//import com.example.filmfestival.ui.theme.LightBackground
//import com.example.filmfestival.ui.theme.WhiteText
//import com.example.filmfestival.utils.NavigationHelper
//
//@Composable
//fun TrailerFullscreen(
//    trailerView: View
//) {
//    Scaffold(
//        backgroundColor = MaterialTheme.colorScheme.background,
//    ) { it ->
//        Box(modifier = Modifier.fillMaxSize().padding(it)){
//            AndroidView(
//                factory = { trailerView }
//            )
//        }
//    }
//}