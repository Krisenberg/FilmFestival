package com.example.filmfestival.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.filmfestival.MainViewModel
import com.example.filmfestival.composables.BottomNavBar
import com.example.filmfestival.utils.NavigationHelper
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch

@Composable
fun EditAvatar(
    navHelper: NavigationHelper,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
){
    Scaffold(
        bottomBar = { BottomNavBar(navHelper = navHelper)}
    ) { paddingValues ->

        val scope = rememberCoroutineScope()
        val actors = remember { mutableStateOf<List<Pair<Int, String>>?>(null)}
        val userId = 1
        val showDialog = remember { mutableStateOf(false) }
        val selectedPhotoUrl = remember { mutableStateOf<String?>(null) }

        LaunchedEffect(scope){
            actors.value = viewModel.getActorsIdPhoto()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Button(
                    onClick = {
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
            actors.value?.let { actorsList ->
                if (actorsList.isNotEmpty()) {
                    LazyColumn (
                        modifier = Modifier
                            .padding(8.dp),
                        verticalArrangement = Arrangement.Center
                    ){
                        items(actorsList.chunked(2)) { chunk ->
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                chunk.forEach { (actorId, actorPhotoUrl) ->
                                    Spacer(modifier = Modifier.weight(1f))
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(actorPhotoUrl)
                                            .build(),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(150.dp)
                                            .padding(4.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable {
                                                selectedPhotoUrl.value = actorPhotoUrl
                                                showDialog.value = true
                                            },
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }
        }
        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = {
                    Text(
                        text = "Do you want to set this image as a profile picture?",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                confirmButton = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    ) {
                        Button(
                            onClick = {
                                selectedPhotoUrl.value?.let { photoUrl ->
                                    scope.launch {
                                        viewModel.changeAvatar(userId, photoUrl)
                                    }
                                }
                                showDialog.value = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = MaterialTheme.colorScheme.onBackground),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        ) {
                            Text("Yes", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        }
                        Button(
                            onClick = { showDialog.value = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = MaterialTheme.colorScheme.onBackground),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("No", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        }
                    }
                }
            )
        }
    }
}