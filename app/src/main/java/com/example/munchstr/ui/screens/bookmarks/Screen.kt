package com.example.munchstr.ui.screens.bookmarks

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.navigation.NavHostController
import com.example.munchstr.R
import com.example.munchstr.model.Recipe
import com.example.munchstr.ui.components.AppCard
import com.example.munchstr.ui.components.SavedCards
import com.example.munchstr.ui.screens.home.handleCardClick
import com.example.munchstr.viewModel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Bookmarks(navController: NavHostController, recipeViewModel: RecipeViewModel) {

    LaunchedEffect(Unit){
        recipeViewModel.loadRecipes()
    }
    val recipes = recipeViewModel.recipes
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    BoxWithConstraints(modifier = Modifier.fillMaxWidth(),
                    ) {
                        val dynamicSize =
                            with(LocalDensity.current) {   if (constraints.maxWidth.toDp() > 600.dp) {
                                0.05f * constraints.maxWidth.toDp()
                            } else {
                                0.1f * constraints.maxWidth.toDp()
                            }}

                        Row(horizontalArrangement = Arrangement.Start) {
                            Image(
                                painter = painterResource(
                                    id = R.drawable
                                        .bookmark
                                ),
                                contentDescription = "",
                                modifier = Modifier.size(dynamicSize)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Saved"
                            )

                        }
                    }
                },
                modifier = Modifier
                    .background
                        (MaterialTheme.colorScheme.surface)
                    .shadow(
                        elevation = 5.dp
                    ),
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.padding(it)) {
            LazyColumn(
                content = {
                    items(
                        items = recipes,
                        key = {item: Recipe ->  item.uuid}
                    ){recipe ->
                        SavedCards(recipe = recipe, onClick = {
                            handleCardClick(navController, recipe.uuid)
                        })
                    }
                }
            )
        }
    }

}
