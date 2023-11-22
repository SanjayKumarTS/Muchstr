package com.example.munchstr.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Insert
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Query("DELETE FROM recipes WHERE uuid = :uuid")
    suspend fun deleteRecipeByUuid(uuid: String)
}