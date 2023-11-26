package com.example.munchstr.model


data class Recipe(
    val uuid: String,
    val authorId: String,
    val name: String,
    val description: String,
    val ingredients: List<Ingredient>,
    val instructions: List<String>,
    val nutrition: List<Nutrition>,
    val preparationTime: Int,
    val cookTime: Int,
    val photo: String,
    val tags: List<String>,
    val createdAt: String
)

data class PostRecipe(
    val uuid: String,
    val authorId: String,
    val name: String,
    val description: String,
    val ingredients: List<Ingredient>,
    val instructions: List<String>,
    val nutrition: List<Nutrition>,
    val preparationTime: Int,
    val cookTime: Int,
    val photo: String,
    val tags: List<String>,
)