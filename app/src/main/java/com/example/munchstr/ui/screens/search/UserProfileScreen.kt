package com.example.munchstr.ui.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestState
import com.example.munchstr.R
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

            Box(modifier = Modifier
                .width(95.dp)
                .height
                    (95.dp)
                .clip(CircleShape)
                .clipToBounds()) {

                AppGlideSubcomposition(imageUri = imageUrl)

            }

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