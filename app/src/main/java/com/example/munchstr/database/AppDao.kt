package com.example.munchstr.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.munchstr.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes where uuid=:uuid")
    suspend fun getRecipeByUuid(uuid: String): Recipe
    @Insert
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Query("DELETE FROM recipes WHERE uuid = :uuid")
    suspend fun deleteRecipeByUuid(uuid: String)
}

@Dao
interface AuthorDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthor(author: AuthorEntity)

    @Delete
    suspend fun deleteAuthor(author: AuthorEntity)

    @Query("SELECT * FROM authors")
    fun getAllAuthors(): Flow<List<AuthorEntity>>
}