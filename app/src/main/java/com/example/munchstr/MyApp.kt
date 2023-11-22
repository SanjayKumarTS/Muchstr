package com.example.munchstr

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.munchstr.ui.navigation.NavigationRoutes
import com.example.munchstr.ui.screens.bookmarks.Bookmarks
import com.example.munchstr.ui.screens.editProfile.EditProfile
import com.example.munchstr.ui.screens.home.HomePage
import com.example.munchstr.ui.screens.login.Login
import com.example.munchstr.ui.screens.recipeDetails.RecipeDetails
import com.example.munchstr.ui.screens.userProfile.UserProfile
import com.example.munchstr.viewModel.RecipeViewModel
import com.example.munchstr.viewModel.SignInViewModel

@Composable
fun MyApp(){
    val navController = rememberNavController()
    val signInViewModel: SignInViewModel = hiltViewModel()
    val recipeViewModel: RecipeViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination =
    NavigationRoutes.LOGIN){
        composable(NavigationRoutes.LOGIN){
            Log.d("Navigation","To Login Screen")
            Login(navController = navController,signInViewModel=signInViewModel)
        }
        composable(NavigationRoutes.HOME_ROUTE){
            Log.d("Navigation","To Home Screen")
            HomePage(navController = navController, recipeViewModel = recipeViewModel,
                signInViewModel = signInViewModel)
        }
        composable("${NavigationRoutes.RECIPE_DETAILS_ROUTE}/{recipeId}") {
            Log.d("Navigation","To Recipe Details")
            RecipeDetails(navController = navController, recipeViewModel = recipeViewModel, recipeId = it.arguments?.getString("recipeId").toString())
        }
        composable(NavigationRoutes.EDIT_PROFILE){
            Log.d("Navigation","To Edit Profile Screen")
            EditProfile(navController = navController, signInViewModel = signInViewModel)
        }
        composable(NavigationRoutes.BOOKMARKS){
            Log.d("Navigation","To Saved Recipes Screen")
            Bookmarks(navController = navController, recipeViewModel = recipeViewModel)
        }
        composable(NavigationRoutes.USER_PROFILE){
            Log.d("Navigation","To User Profile Screen")
            UserProfile(
                navController = navController,
                signInViewModel = signInViewModel,
                recipeViewModel = recipeViewModel
            )
        }
    }
}