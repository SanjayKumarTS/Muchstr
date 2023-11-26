package com.example.munchstr.ui.screens.home

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.munchstr.R
import com.example.munchstr.model.ResponseFindRecipesForUserDTO
import com.example.munchstr.ui.components.AppCard
import com.example.munchstr.ui.components.AppGlideSubcomposition
import com.example.munchstr.ui.navigation.NavigationRoutes
import com.example.munchstr.viewModel.RecipeViewModel
import com.example.munchstr.viewModel.SignInViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun HomePage(
    navController: NavHostController,
    recipeViewModel: RecipeViewModel,
    signInViewModel: SignInViewModel
) {
    val recipes:List<ResponseFindRecipesForUserDTO> = recipeViewModel.recipesForCards
    val userInfo by signInViewModel.userData.collectAsState()

    LaunchedEffect(Unit){
        if(recipes.isEmpty() && !recipeViewModel.isRefreshing.value){
            userInfo?.let { recipeViewModel.loadRecipesForUser(it.uuid) }
            Log.d("HomePage","$userInfo")
        }
    }

    val ptrState=
        rememberPullRefreshState(recipeViewModel.isRefreshing.value, { userInfo?.let {
            recipeViewModel.loadRecipesForUser(
                it.uuid)
        } })
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable
                            .munchstr),
                            contentDescription = ""
                        )
                        Box(modifier = Modifier.padding(20.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = {
                                    navController.navigate(NavigationRoutes.SEARCH_ROUTE)
                                }) {
                                    Icon(Icons.Filled.Search,
                                        contentDescription = "Search",
                                        tint = Color(123, 123, 125)
                                    )
                                }
                                Box(modifier = Modifier
                                    .width(45.dp)
                                    .height
                                        (45.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                    navController.navigate(NavigationRoutes
                                        .USER_PROFILE)
                                }) {
                                    userInfo?.photoURL?.let {
                                        AppGlideSubcomposition(
                                            imageUri = it,
                                            modifier = Modifier.clipToBounds()
                                        )
                                    }
                                }
                            }

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
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Box(modifier = Modifier
                .pullRefresh(ptrState)
                .fillMaxSize()) {
            LazyColumn(
                content = {
                    items(
                        items = recipes,
                        key = {item: ResponseFindRecipesForUserDTO ->  item.uuid}
                    ) { recipe ->
                        recipe.userLiked?.let { it1 ->
                            AppCard(
                                author = recipe.author,
                                recipe = recipe.recipe,
                                likesCount = recipe.likesCount,
                                commentsCount = recipe.commentsCount,
                                creationTime = recipe.recipe.creationTime,
                                isLiked = it1,
                                onLikeClicked = {
                                    // Handle like button click here
                                    // For example, update the like status in the view model
                                    userInfo?.let { it1 -> recipeViewModel.addLike(recipe.uuid, it1.uuid) }
                                },
                                onClick = {
                                    recipeViewModel.updateSelectedRecipeAuthor(recipe.author)
                                    handleCardClick(
                                        navController,
                                        recipeId = recipe.uuid,
                                        likesCount = recipe.likesCount,
                                        commentsCount = recipe.commentsCount,
                                    )
                                },
                                modifier = Modifier.animateItemPlacement
                                    (animationSpec = tween(durationMillis =
                                600)
                                )
                            )
                        }
                    }
                }
            )
            PullRefreshIndicator(
                recipeViewModel.isRefreshing.value, ptrState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }
        }
}

fun handleCardClick(
    navController: NavController,
    recipeId: String,
    likesCount: Int=0,
    commentsCount: Int=0,
    isLiked:Boolean=false
){
    navController.navigate(NavigationRoutes.recipeDetailsRoute(
        recipeId = recipeId,
        likesCount = likesCount,
        commentsCount = commentsCount,
        isLiked = isLiked
    ))
}

//@Preview(showBackground = true)
//@Composable
//fun HomePagePreview(){
//    HomePage(navController=NavHostController())
//}
