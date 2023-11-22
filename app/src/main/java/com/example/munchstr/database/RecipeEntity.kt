package com.example.munchstr.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.munchstr.model.Nutrition
import com.example.munchstr.model.Ingredient

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey val uuid: String,
    val name: String,
    val description: String,
    val authorId: String,
    val ingredients: List<Ingredient>,
    val instructions: List<String>,
    val nutrition: List<Nutrition>,
    val preparationTime: Int,
    val cookTime: Int,
    val photo: String,
    val tags: List<String>
)


