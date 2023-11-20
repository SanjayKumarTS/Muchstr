package com.example.munchstr.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LikeButton(
    modifier: Modifier = Modifier, tint: Color = MaterialTheme.colorScheme.primary
){

    var liked by remember {mutableStateOf(false)}

    IconButton(onClick = {
        liked = !liked
    }) {
        Icon(
            imageVector =if(liked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "Like",
//            tint = tint
        )
    }
}