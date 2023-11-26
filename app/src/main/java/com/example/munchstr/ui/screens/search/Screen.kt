package com.example.munchstr.ui.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.munchstr.R
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.munchstr.ui.components.AppGlideSubcomposition
import com.example.munchstr.ui.components.SearchBar
import com.example.munchstr.ui.navigation.NavigationRoutes
import com.example.munchstr.viewModel.RecipeViewModel
import com.example.munchstr.viewModel.SignInViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(navController: NavController, recipeViewModel:
RecipeViewModel, signInViewModel: SignInViewModel) {
    var searchedString by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        SearchBar(
                            searchString = searchedString,
                            onSearchChanged = { newValue ->
                                searchedString = newValue
                            },
                            onSearchClicked = { searchString ->
                                recipeViewModel.searchRecipe(searchString)
                            }
                        )
                    }
                },
                modifier = Modifier
                    .background
                        (MaterialTheme.colorScheme.surface)
                    .shadow(
                        elevation = 5
                            .dp
                    ),
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(NavigationRoutes.Add_RECIPE)
            }, containerColor =
            MaterialTheme.colorScheme.primary) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "",

                    )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Column (modifier = Modifier.padding(it)){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                Text("Categories", style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.height(16.dp))


                Row(modifier = Modifier.fillMaxWidth()) {
                    CategoryCard(
                        categoryName = "Breakfast",
                        modifier = Modifier
                            .weight(1f)
                            .height(IntrinsicSize.Min),
                        onClick = {},
                        skillet = R.drawable.breakfast_dining,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    CategoryCard(
                        categoryName = "Lunch",
                        modifier = Modifier
                            .weight(1f)
                            .height(IntrinsicSize.Min),
                        onClick = {},
                        skillet = R.drawable.ramen_dining
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    CategoryCard(
                        categoryName = "Curry", modifier = Modifier
                            .weight(1f)
                            .height(IntrinsicSize.Min), onClick = {},
                        R.drawable.skillet
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    CategoryCard(
                        categoryName = "Snacks",
                        modifier = Modifier
                            .weight(1f)
                            .height(IntrinsicSize.Min),
                        onClick = {},
                        skillet = R.drawable.icecream
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                CategoryCard(
                    categoryName = "Desserts", modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    onClick = {},
                    skillet = R.drawable.cake
                )
            }
        }
    }
}

@Composable
fun CategoryCard(
    categoryName: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    skillet: Int
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .clickable { },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(categoryName)
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = skillet),
                contentDescription = "Skillet",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


