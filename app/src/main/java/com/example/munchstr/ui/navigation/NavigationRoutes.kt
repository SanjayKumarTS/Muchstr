package com.example.munchstr.ui.navigation

object NavigationRoutes {
    const val HOME_ROUTE = "home"
    const val RECIPE_DETAILS_ROUTE = "recipeDetails"
    const val SEARCH_ROUTE = "CategoryScreen"
    const val LOGIN = "login"
    const val EDIT_PROFILE = "editProfile"
    const val BOOKMARKS = "Bookmarks"
    const val USER_PROFILE = "UserProfile"
    const val Add_RECIPE = "AddRecipe"

    fun recipeDetailsRoute(
        recipeId: String,
        likesCount: Int,
        commentsCount: Int,
        isLiked: Boolean
    ): String {
        return "$RECIPE_DETAILS_ROUTE/$recipeId/$likesCount/$commentsCount/$isLiked"
    }
}