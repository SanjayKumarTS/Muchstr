package com.example.munchstr.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.munchstr.R
import com.example.munchstr.model.CommentDto

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