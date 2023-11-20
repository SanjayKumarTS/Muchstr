package com.example.munchstr.model

import com.example.munchstr.ui.screens.addRecipe.Ingredient

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
    val tags: List<String>
)
