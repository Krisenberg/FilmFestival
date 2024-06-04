package com.example.filmfestival.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.filmfestival.MainViewModel
import com.example.filmfestival.R
import com.example.filmfestival.composables.BottomNavBar
import com.example.filmfestival.utils.NavigationHelper
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.filmfestival.utils.NavigationRoutes

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EditUser(
    navHelper: NavigationHelper,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val username by viewModel.getUsername(1).collectAsStateWithLifecycle(initialValue = "")
    val newUsername = rememberSaveable { mutableStateOf(username) }
    val avatarUrl by viewModel.getAvatar(1).collectAsStateWithLifecycle(initialValue = "")

    LaunchedEffect(username) {
        if (username.isNotEmpty()) {
            newUsername.value = username
        }
    }

    Scaffold(
        bottomBar = { BottomNavBar(navHelper = navHelper) }
    ) { paddingValues ->
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
            Spacer(modifier = Modifier.height(50.dp))
            RoundImageWithPencil(
                avatarUrl = avatarUrl,
                modifier = Modifier
                    .size(200.dp)
                    .padding(5.dp)
                    .align(Alignment.CenterHorizontally),
                onEditClick = {
                    navHelper.navigate(NavigationRoutes.EDIT_AVATAR)
                }
            )
            Spacer(modifier = Modifier.height(50.dp))

            val isEditing = remember { mutableStateOf(false) }
            BasicTextField(
                value = newUsername.value,
                onValueChange = { newUsername.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .align(Alignment.CenterHorizontally),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = androidx.compose.ui.text.input.ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        isEditing.value = false
                        viewModel.changeUsername(1, newUsername.value)
                    }
                ),
                textStyle = LocalTextStyle.current.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            )
            LaunchedEffect(isEditing.value) {
                if (isEditing.value) {
                    viewModel.changeUsername(1, newUsername.value)
                }
            }
        }
    }
}

@Composable
fun RoundImageWithPencil(
    avatarUrl: String,
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
    ) {
        AsyncImage(
            model = avatarUrl,
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f, matchHeightConstraintsFirst = true)
                .fillMaxSize()
                .align(Alignment.Center),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                .align(Alignment.Center)
                .clickable { onEditClick() }
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                tint = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
