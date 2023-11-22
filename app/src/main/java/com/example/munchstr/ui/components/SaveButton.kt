package com.example.munchstr.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.munchstr.R

@Composable
fun SaveButton(onClick: () -> Unit){
    IconButton(onClick =  onClick) {
        Icon(painter = painterResource(id = R.drawable.bookmark_add),
            contentDescription =
        "Bookmark")
    }
}