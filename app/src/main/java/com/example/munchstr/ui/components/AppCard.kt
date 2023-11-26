package com.example.munchstr.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.munchstr.R
import com.example.munchstr.model.Author
import com.example.munchstr.model.RecipeInCards

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AppCard(
    recipe: RecipeInCards,
    onClick: () -> Unit,
    modifier: Modifier,
    likesCount: Int,
    commentsCount: Int,
    creationTime: String,
    author: Author,
    isLiked: Boolean, // Add this
    onLikeClicked: (Boolean) -> Unit
) {
    ElevatedCard(onClick = onClick , modifier = modifier
        .fillMaxWidth()
        .padding(15.dp)
    ) {
        UserIconAndName(author.name,author.photo, creationTime)
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        ) {
            AppGlideSubcomposition(imageUri = recipe.photo, modifier =
            Modifier.matchParentSize())
        }
        Column(modifier = Modifier.padding(horizontal = 20.dp)){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = recipe.name, style = MaterialTheme.typography
                    .titleLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                LikeButton(
                    tint = Color(28,27,31),
                    liked = isLiked,
                    onClick = onLikeClicked
                )
            }
            Text(
                text = recipe.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 10.dp),
                color = Color(147,142,142)
            )
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)) {
                Row (verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = "$likesCount Likes",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Image(painter = painterResource(id = R.drawable
                        .check_indeterminate_small) ,
                        contentDescription = "")
                    Text(
                        text = "$commentsCount Comments",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                Button(onClick = { onClick }, colors = ButtonDefaults
                    .buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    )
                ) {
                    Text(text = "View Recipe")
                }
            }
        }
    }
}

//@Composable
//@Preview(showBackground = false)
//fun AppCardPreview(){
//    AppCard(recipe)
//}