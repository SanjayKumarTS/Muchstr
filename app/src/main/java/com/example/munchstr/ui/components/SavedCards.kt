package com.example.munchstr.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.munchstr.model.Author
import com.example.munchstr.model.Recipe
import com.example.munchstr.ui.navigation.NavigationRoutes
import com.example.munchstr.utils.formatCreationTime

@Composable
fun SavedCards(
    recipe: Recipe,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier=Modifier,
    author: Author,
    navController:NavController
){
    ElevatedCard(onClick = onClick , modifier = modifier
        .fillMaxWidth()
        .padding(15.dp)
    )
    {
        Box(modifier = Modifier.fillMaxWidth()) {
            UserIconAndName(author.name,author.photo, creationTime = recipe.createdAt,
                onUserIconClicked = {
                    navController.navigate("${NavigationRoutes.USER_PROFILE}/${author.uuid}")
                }
            )
            IconButton(onClick = { onDelete() },
                modifier = Modifier.align(Alignment.TopEnd)) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete")
            }
        }

        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        ) {
            AppGlideSubcomposition(imageUri = recipe.photo)
        }
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)){
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

                }
                Button(onClick =  onClick , colors = ButtonDefaults
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