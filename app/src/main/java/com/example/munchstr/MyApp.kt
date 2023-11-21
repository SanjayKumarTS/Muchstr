package com.example.munchstr

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.munchstr.ui.screens.bookmarks.Bookmarks
import com.example.munchstr.ui.navigation.NavigationRoutes
import com.example.munchstr.ui.screens.editProfile.EditProfile
import com.example.munchstr.ui.screens.home.HomePage
import com.example.munchstr.ui.screens.recipeDetails.RecipeDetails
import com.example.munchstr.ui.screens.userProfile.UserProfile
import com.example.munchstr.viewModel.RecipeViewModel

@Composable
fun MyApp(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination =
    NavigationRoutes.HOME_ROUTE){
        composable(NavigationRoutes.HOME_ROUTE){
            val recipeViewModel: RecipeViewModel = hiltViewModel()
            HomePage(navController, recipeViewModel)
        }
        composable("${NavigationRoutes.RECIPE_DETAILS_ROUTE}/{recipeId}"){
            val recipeViewModel: RecipeViewModel = hiltViewModel(it)
            RecipeDetails(navController,recipeViewModel, it.arguments?.getString("recipeId")
                .toString())
        }
        composable(NavigationRoutes.USER_INFO) {
            val recipeViewModel: RecipeViewModel = hiltViewModel()
            UserProfile(navController ,recipeViewModel )
        }
        composable(NavigationRoutes.COLLECTIONS) {
            val recipeViewModel: RecipeViewModel = hiltViewModel()
            Bookmarks(navController ,recipeViewModel )
        }
        composable(NavigationRoutes.EDIT_PROFILE) {
            EditProfile(navController)
        }


    }
}


