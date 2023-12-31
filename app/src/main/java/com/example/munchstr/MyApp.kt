package com.example.munchstr

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.munchstr.ui.navigation.NavigationRoutes
import com.example.munchstr.ui.screens.addRecipe.AddRecipe
import com.example.munchstr.ui.screens.bookmarks.Bookmarks
import com.example.munchstr.ui.screens.editProfile.EditProfile
import com.example.munchstr.ui.screens.home.HomePage
import com.example.munchstr.ui.screens.login.Login
import com.example.munchstr.ui.screens.recipeDetails.RecipeDetails
import com.example.munchstr.ui.screens.search.CategoryScreen
import com.example.munchstr.ui.screens.userProfile.UserProfile
import com.example.munchstr.viewModel.CommentViewModel
import com.example.munchstr.viewModel.FAndFViewModel
import com.example.munchstr.viewModel.RecipeViewModel
import com.example.munchstr.viewModel.SignInViewModel

@Composable
fun MyApp(){

    val navController = rememberNavController()
    val signInViewModel: SignInViewModel = hiltViewModel()
    val recipeViewModel: RecipeViewModel = hiltViewModel()
    val commentViewModel: CommentViewModel = hiltViewModel()
    val fandFViewModel: FAndFViewModel = hiltViewModel()


    fun enterTransition(): EnterTransition = slideInHorizontally(
        initialOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(700)
    )
    fun exitTransition(): ExitTransition = slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = tween(700)
    )

    fun popEnterTransition(): EnterTransition = slideInHorizontally(
        initialOffsetX = { -it },
        animationSpec = tween(700)
    )

    fun popExitTransition(): ExitTransition = slideOutHorizontally(
        targetOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(700)
    )

    NavHost(navController = navController, startDestination =
    NavigationRoutes.LOGIN){
        composable(
            route = NavigationRoutes.LOGIN,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ){
            Log.d("Navigation","To Login Screen")
            Login(navController = navController,signInViewModel=signInViewModel)
        }
        composable(
            route = NavigationRoutes.HOME_ROUTE,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ){
            Log.d("Navigation","To Home Screen")
            HomePage(navController = navController, recipeViewModel = recipeViewModel,
                signInViewModel = signInViewModel)
        }
        composable(
            route = "${NavigationRoutes.RECIPE_DETAILS_ROUTE}/{recipeId}/{likesCount}/{commentsCount" +
                    "}/{isLiked}",
            arguments = listOf(
                navArgument("recipeId") { type = NavType.StringType },
                navArgument("likesCount") { type = NavType.IntType },
                navArgument("commentsCount") { type = NavType.IntType },
                navArgument("isLiked") {type = NavType.BoolType},
            ),
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId") ?: ""
            val likesCount = backStackEntry.arguments?.getInt("likesCount") ?: 0
            val commentsCount = backStackEntry.arguments?.getInt("commentsCount") ?: 0
            val isLiked = backStackEntry.arguments?.getBoolean("isLiked")?:false
            Log.d("Navigation","To Recipe Details")
            RecipeDetails(
                navController = navController,
                recipeViewModel = recipeViewModel,
                recipeId = recipeId,
                likesCount = likesCount,
                commentsCount = commentsCount,
                commentViewModel = commentViewModel,
                isLiked = isLiked,
                signInViewModel = signInViewModel
            )
        }
        composable(
            route = NavigationRoutes.EDIT_PROFILE,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ){
            Log.d("Navigation","To Edit Profile Screen")
            EditProfile(navController = navController, signInViewModel = signInViewModel)
        }
        composable(
            route = NavigationRoutes.BOOKMARKS,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ){
            Log.d("Navigation","To Saved Recipes Screen")
            Bookmarks(navController = navController, recipeViewModel = recipeViewModel)
        }
        composable(
            route="${NavigationRoutes.USER_PROFILE}/{userId}",
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) {
                backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            UserProfile(
                navController = navController,
                signInViewModel = signInViewModel,
                recipeViewModel = recipeViewModel,
                selectedUserId = userId,
                fandFViewModel= fandFViewModel
            )
        }
        composable(
            route = NavigationRoutes.Add_RECIPE,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ){
            AddRecipe(recipeViewModel = recipeViewModel, navController =
            navController, signInViewModel = signInViewModel)
        }
        composable(
            route = NavigationRoutes.SEARCH_ROUTE,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ){
            CategoryScreen(navController, recipeViewModel = recipeViewModel,
                signInViewModel = signInViewModel)
        }
    }
}
