package com.example.munchstr.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.munchstr.R
import com.example.munchstr.model.RecipeInCards

@Composable
fun UserIconAndName(name:String, photo:String, creationTime: String) {
    val dateRegex = Regex("^\\w{3} \\w{3} \\d{2} \\d{4}")
    val dateString = dateRegex.find(creationTime)?.groups?.get(0)?.value.toString()
    Row (modifier = Modifier
        .height(IntrinsicSize.Max)
        .padding(10.dp)){
        Box(modifier = Modifier
            .width(45.dp)
            .height
                (45.dp)
            .clip(CircleShape)
            .clipToBounds()) {
            AppGlideSubcomposition(imageUri = photo)
        }
        Column(modifier = Modifier
            .fillMaxHeight()
            .padding(start = 10.dp),
            verticalArrangement =
        Arrangement
            .SpaceEvenly) {
            Text(text = name, style = MaterialTheme
                .typography.titleSmall, color = MaterialTheme.colorScheme.onSecondaryContainer)
            Text(text = dateString, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface)

        }
    }
}

//@Composable
//@Preview(showBackground = true)
//fun UserIconAndNamePreview(){
//    UserIconAndName(recipe, creationTime)
//}