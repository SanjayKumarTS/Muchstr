package com.example.munchstr.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.munchstr.ui.components.AppGlideSubcomposition


@Composable
fun UserProfileCard( name: String, bio: String, imageUrl: String, onClick: () -> Unit) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AppGlideSubcomposition(
                imageUri = imageUrl,
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 16.dp),
            )

            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = name, color = Color.Black, style = MaterialTheme.typography.h6)
                Text(text = bio, color = Color.Gray, style = MaterialTheme.typography.body2)
            }
        }
    }
}