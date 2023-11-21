package com.example.munchstr.ui.screens.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.munchstr.R
import com.example.munchstr.model.Recipe
import com.example.munchstr.ui.components.AppCard
import com.example.munchstr.ui.navigation.NavigationRoutes
import com.example.munchstr.ui.screens.search.CategoryScreen
import com.example.munchstr.viewModel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavHostController, recipeViewModel: RecipeViewModel) {
    var showSearch by rememberSaveable { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf(TextFieldValue()) }
    var selectedCategory by remember { mutableStateOf("Recipes") }


    LaunchedEffect(Unit) {
        recipeViewModel.loadRecipes()
    }
    val recipes = recipeViewModel.recipes
    Scaffold(
        topBar = {

            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(animationSpec = tween(durationMillis = 300))
                        , verticalArrangement = Arrangement.Top
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (!showSearch) {
                                Image(
                                    painter = painterResource(id = R.drawable.munchstr),
                                    contentDescription = ""
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (showSearch) {
                                    OutlinedTextField(
                                        value = searchQuery.value,
                                        onValueChange = { searchQuery.value = it },
                                        modifier = Modifier.fillMaxWidth(0.7f), // Adjusted for potential padding
                                        placeholder = { Text("Search") },
                                        trailingIcon = {
                                            IconButton(onClick = { /* invoke your search function */ }) {
                                                Icon(Icons.Default.Search, contentDescription = "Search")
                                            }
                                        }
                                    )
                                }
                                IconButton(onClick = { showSearch = !showSearch }) {
                                    Icon(
                                        imageVector = if (showSearch) Icons.Default.Close else Icons.Filled.Search,
                                        contentDescription = if (showSearch) "Close search" else "Open search",
                                        tint = Color(123, 123, 125)
                                    )
                                }
                                    Image(
                                        painter = painterResource(id = R.drawable.dall_e_2023_10_19_10_14_13___photo_profile_pic_of_a_young_man_with_tan_skin__spiky_black_hair__and_hazel_eyes__he_has_a_playful_smirk_and_is_wearing_a_gray_t_shirt),
                                        contentDescription = "Avatar",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(CircleShape)
                                            .clickable {  navController.navigate("user_info") }
                                    )
                            }
                        }
                    }
                },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .shadow(elevation = 5.dp)

            )
        },


        floatingActionButton = {
            if(!showSearch) {
                FloatingActionButton(
                    onClick = { /*TODO*/ }, containerColor =
                    MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "",

                        )
                }
            }
        }

        ,
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.padding(it)) {
            if (showSearch) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    Button(
                        onClick = { selectedCategory = "People" },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedCategory == "People") Color.Red else Color.LightGray,
                            contentColor = Color.White
                        )
                    ) {
                        Text("People")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = { selectedCategory = "Recipes" },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedCategory == "Recipes") Color.Red else Color.LightGray,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Recipes")
                    }

                }

                CategoryScreen()
            } else {
                LazyColumn(
                    content = {
                        items(
                            items = recipes,
                            key = { item: Recipe -> item.uuid }
                        ) { recipe ->
                            AppCard(recipe = recipe, onClick = {
                                handleCardClick(navController, recipe.uuid)
                            })
                        }
                    }
                )
            }
        }
    }
}

fun onSearchClicked() {
    TODO("Not yet implemented")
}

fun handleCardClick(navController: NavController, uuid: String){
    navController.navigate(NavigationRoutes.recipeDetailsRoute(recipeId = uuid))
}

//@Preview(showBackground = true)
//@Composable
//fun HomePagePreview(){
//    HomePage(navController=NavHostController())
//}
