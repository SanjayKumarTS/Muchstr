package com.example.munchstr.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.munchstr.R

@Composable
fun CommentButton(
    modifier: Modifier = Modifier, onClick: () -> Unit
){


    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.forum),
            contentDescription = "Like"
        )
    }
}