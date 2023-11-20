package com.example.munchstr.model

data class RecipeResponse(
    val recipes: List<Recipe>,
    val currentPage: Int,
    val totalPages: Int,
    val totalResults: Int
)
