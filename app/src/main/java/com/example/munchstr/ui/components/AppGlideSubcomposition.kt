package com.example.munchstr.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestState
import com.example.munchstr.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AppGlideSubcomposition(imageUri: String){
    Box()
    {
        GlideSubcomposition(
            model = imageUri,
        ) {
            when (state) {
                RequestState.Failure -> Image(
                    painter = painterResource(id = R.drawable.baseline_warning_amber_24),
                    contentDescription = "placeholder",
                    modifier = Modifier.fillMaxSize()
                )

                RequestState.Loading ->
                    Box(modifier = Modifier.matchParentSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                is RequestState.Success -> Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                else -> {}
            }
        }
    }
}