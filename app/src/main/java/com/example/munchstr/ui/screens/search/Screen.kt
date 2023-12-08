package com.example.munchstr.ui.screens.search

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.toLowerCase
import com.example.munchstr.R
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.munchstr.model.ResponseFindRecipesForUserDTO
import com.example.munchstr.model.UserForProfile
import com.example.munchstr.ui.components.AppCard
import com.example.munchstr.ui.components.SearchBar
import com.example.munchstr.ui.navigation.NavigationRoutes
import com.example.munchstr.ui.screens.home.handleCardClick
import com.example.munchstr.viewModel.RecipeViewModel
import com.example.munchstr.viewModel.SignInViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CategoryScreen(navController: NavController, recipeViewModel:
RecipeViewModel, signInViewModel: SignInViewModel) {

    val userProfiles = signInViewModel.searchUserData
    val recipedata = recipeViewModel.recipesForCards
    val currentuser = signInViewModel.userData

    val allButtons = listOf(
        "People", "Recipes", "Breakfast", "Lunch", "Snacks", "Desserts"
    )

    val lazyState = rememberLazyListState()


    val selectedCategory = recipeViewModel.selectedCategory.collectAsState().value


    LaunchedEffect(Unit){
        Log.d("SearchScreen", "Cleared")
        recipeViewModel.clearRecipesforCards()
    }

    var searchedString by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                        signInViewModel.clearSearchUserData()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        SearchBar(
                            recipeViewModel= recipeViewModel,
                            searchString = searchedString,
                            onSearchChanged = { newValue ->
                                searchedString = newValue
                            }
                        ) { searchString ->
                            signInViewModel.searchUsers(searchString)
                            recipeViewModel.searchRecipes(searchedString)
                        }
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
            FloatingActionButton(
                onClick = {
                    navController.navigate(NavigationRoutes.Add_RECIPE)
                }, containerColor =
                MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "",
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        content =
        { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                if (userProfiles.isNotEmpty() || recipedata.isNotEmpty()) {
                    val buttonsToShow = if (recipeViewModel.selectedButton.value == false) {
                        allButtons.drop(2)
                    } else {
                        allButtons.dropLast(4)
                    }
                    buttonsToShow.chunked(3).forEach { rowButtons ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            rowButtons.forEach { label ->
                                OutlinedButton(
                                    onClick = {
                                        if (label != "People" && label != "Recipes") {
                                            if (recipeViewModel.selectedCategory.value != label) {
                                                recipeViewModel.searchRecipesbyCategory(label)
                                                recipeViewModel.selectCategory(label)
                                            } else {
                                                recipeViewModel.selectCategory("")
                                            }
                                        } else {
                                            if (recipeViewModel.selectedCategory.value != label) {

                                                recipeViewModel.selectCategory(label)

                                            } else {
                                                recipeViewModel.selectCategory("")
                                            }
                                        }
                                    },
                                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = if (selectedCategory == label && selectedCategory!="")
                                            MaterialTheme.colorScheme.primary
                                        else
                                            MaterialTheme.colorScheme.onSurface
                                    )
                                ) {
                                    Text(label)
                                }
                            }
                        }
                    }
                }

                if (userProfiles.isNotEmpty() || recipedata.isNotEmpty()) {
                    LazyColumn {
                        if (userProfiles.isNotEmpty() && recipeViewModel.selectedCategory.value != "Recipes") {
                            itemsIndexed(items = userProfiles) { index, userProfile ->
                                UserProfileCard(
                                    name = userProfile.name ?: "",
                                    bio = userProfile.bio ?: "",
                                    imageUrl = userProfile.photo ?: "",
                                    onClick = {
                                        navController.navigate("${NavigationRoutes.USER_PROFILE}/${userProfile.uuid}")
                                    }
                                )
                            }
                        }

                        if (recipedata.isNotEmpty() && recipeViewModel.selectedCategory.value != "People" ) {
                            items(
                                items = recipedata,
                                key = { item: ResponseFindRecipesForUserDTO -> item.uuid }
                            ) { recipe ->
                                recipe.userLiked?.let { isLiked ->
                                    AppCard(
                                        author = recipe.author,
                                        recipe = recipe.recipe,
                                        uuid= recipe.uuid,
                                        recipeViewModel = recipeViewModel,
                                        creationTime = recipe.recipe.creationTime,
                                        isLiked = isLiked,
                                        onLikeClicked = {
                                            currentuser.value?.uuid?.let { userId ->
                                                val index = recipedata.indexOf(recipe)
                                                if(recipe.userLiked!!){
                                                    recipeViewModel.removeLike(recipe.uuid, userId)
                                                    recipedata[index].userLiked=false
                                                }
                                                else {
                                                    recipeViewModel.addLike(recipe.uuid, userId)
                                                    recipedata[index].userLiked=true

                                                }
                                            }
                                        },
                                        onClick = {
                                            recipeViewModel.updateSelectedRecipeAuthor(recipe.author)
                                            handleCardClick(
                                                navController,
                                                recipe.uuid,
                                                recipe.likesCount,
                                                recipe.commentsCount
                                            )
                                        },
                                        navController = navController,
                                        modifier = Modifier.animateItemPlacement(tween(600))
                                    )
                                }
                            }
                        }
                    }
                } else {


                    DefaultCategoriesLayout(
                        recipeViewModel = recipeViewModel,
                        onCategorySelected = { category -> recipeViewModel.searchRecipesbyCategory(category) }
                    )
                }
            }
        })
}







@Composable
fun DefaultCategoriesLayout(
    recipeViewModel: RecipeViewModel,
    onCategorySelected: (String) -> Unit
)    {var selectedCategory by remember { mutableStateOf<String?>(null) }


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
                isSelected = selectedCategory == "Breakfast",
                modifier = Modifier
                    .weight(1f)
                    .height(IntrinsicSize.Min),
                onClick = { recipeViewModel.selectCategory("Breakfast")
                    onCategorySelected("Breakfast")
                    recipeViewModel.selectbutton(false)
                },
                skillet = R.drawable.breakfast_dining
            )

            Spacer(modifier = Modifier.width(8.dp))
            CategoryCard(
                categoryName = "Lunch",
                isSelected = selectedCategory == "Lunch",
                modifier = Modifier
                    .weight(1f)
                    .height(IntrinsicSize.Min),
                onClick = {recipeViewModel.selectCategory("Lunch")
                    onCategorySelected("Lunch")
                    recipeViewModel.selectbutton(false)

                },
                skillet = R.drawable.ramen_dining
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            CategoryCard(
                categoryName = "Curry",
                isSelected = selectedCategory == "Curry",
                modifier = Modifier
                    .weight(1f)
                    .height(IntrinsicSize.Min),
                onClick = {
                    recipeViewModel.selectCategory("Curry")
                    recipeViewModel.selectbutton(false)
                    selectedCategory = "Curry" },
                R.drawable.skillet
            )
            Spacer(modifier = Modifier.width(8.dp))
            CategoryCard(
                categoryName = "Snacks",
                isSelected = selectedCategory == "Snacks",
                modifier = Modifier
                    .weight(1f)
                    .height(IntrinsicSize.Min),
                onClick = { recipeViewModel.selectCategory("Snacks")
                    onCategorySelected("Snacks")
                    recipeViewModel.selectbutton(false)

                },
                skillet = R.drawable.icecream
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            CategoryCard(
                categoryName = "Desserts",
                isSelected = selectedCategory == "Desserts",
                modifier = Modifier
                    .weight(1f)
                    .height(IntrinsicSize.Min),
                onClick = { recipeViewModel.selectCategory("Desserts")
                    onCategorySelected("Desserts")
                    recipeViewModel.selectbutton(false)
                },
                skillet = R.drawable.cake
            )
        }
    }
}

@Composable
fun CategoryCard(
    categoryName: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    skillet: Int
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .clickable(onClick = onClick)
            .background(if (isSelected) Color.Red else Color.White),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(categoryName)
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = skillet),
                contentDescription = "$categoryName Image",
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(Color.Black)
            )
        }
    }
}