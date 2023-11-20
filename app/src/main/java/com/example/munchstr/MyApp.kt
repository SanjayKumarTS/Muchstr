package com.example.munchstr

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.munchstr.ui.navigation.NavigationRoutes
import com.example.munchstr.ui.screens.home.HomePage
import com.example.munchstr.ui.screens.recipeDetails.RecipeDetails
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
    }
}