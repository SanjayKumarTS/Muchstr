package com.example.munchstr.database

import com.example.munchstr.model.Author
import com.example.munchstr.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getAllRecipes(): Flow<List<Recipe>>
    suspend fun getRecipeByUuid(uuid: String):Recipe
    suspend fun insertRecipe(recipe: Recipe)
    suspend fun deleteRecipeByUuid(uuid: String)
}

interface  AuthorRepository{
    suspend fun insertAuthor(author: Author)
    suspend fun deleteAuthor(author: Author)
    fun getAllAuthors(): Flow<List<Author>>
}