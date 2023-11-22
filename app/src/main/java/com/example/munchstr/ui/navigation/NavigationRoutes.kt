package com.example.munchstr.ui.navigation

object NavigationRoutes {
    const val HOME_ROUTE = "home"
    const val RECIPE_DETAILS_ROUTE = "recipeDetails"
    const val SEARCH_ROUTE = "search"
    const val LOGIN = "login"
    const val EDIT_PROFILE = "editProfile"
    const val BOOKMARKS = "Bookmarks"
    const val USER_PROFILE = "UserProfile"

    fun recipeDetailsRoute(recipeId: String): String {
        return "$RECIPE_DETAILS_ROUTE/$recipeId"
    }
}