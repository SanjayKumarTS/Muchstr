package com.example.munchstr.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.munchstr.R

@Composable
fun SaveButton(isRecipeSaved: Boolean, onSave: () -> Unit, onDelete: () -> Unit) {
    IconButton(onClick = {
        if (isRecipeSaved) {
            onDelete()
        } else {
            onSave()
        }
    }) {
        if (isRecipeSaved) {
            Icon(painter = painterResource(id = R.drawable.baseline_bookmark_added_24),
                contentDescription = "Unsave")
        } else {
            Icon(painter = painterResource(id = R.drawable.bookmark_add), contentDescription = "Save")
        }
    }
}