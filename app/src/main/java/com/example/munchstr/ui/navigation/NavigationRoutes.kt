package com.example.munchstr.ui.navigation

object NavigationRoutes {
    const val HOME_ROUTE = "home"
    const val RECIPE_DETAILS_ROUTE = "recipeDetails"
    const val SEARCH_ROUTE = "search"
    const val LOGIN = "login"
    const val EDIT_PROFILE = "editProfile"

    fun recipeDetailsRoute(recipeId: String): String {
        return "$RECIPE_DETAILS_ROUTE/$recipeId"
    }
}