package com.example.munchstr.model

data class ResponseFindRecipesForUserDTO(
    val uuid: String,
    val author: Author,
    val recipe: RecipeInCards,
    val likesCount: Int,
    val commentsCount: Int
)

data class Author(
    val uuid: String,
    val name: String,
    val photo: String
)

data class RecipeInCards(
    val name: String,
    val description: String,
    val photo: String,
    val creationTime: String
)