package com.example.munchstr.database

import com.example.munchstr.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecipeRepositoryImpl(private val recipeDao: RecipeDao) : RecipeRepository {
    override fun getAllRecipes(): Flow<List<Recipe>> {
        return recipeDao.getAllRecipes().map { entities ->
            entities.map { entity ->
                Recipe(
                    entity.uuid, entity.name, entity.description,entity
                        .authorId, entity.ingredients,entity.instructions,
                    entity.nutrition, entity.preparationTime, entity
                        .cookTime, entity.photo, entity.tags
                )
            }
        }
    }
    override suspend fun insertRecipe(recipe: Recipe) {
        recipeDao.insertRecipe(RecipeEntity(
            name = recipe.name,
            description = recipe.description,
            uuid = recipe.uuid,
            ingredients = recipe.ingredients,
            nutrition = recipe.nutrition,
            preparationTime = recipe.preparationTime,
            cookTime = recipe.cookTime,
            photo = recipe.photo,
            tags = recipe.tags,
            authorId = recipe.authorId,
            instructions = recipe.instructions
        ))
    }
    override suspend fun deleteRecipeByUuid(uuid: String) {
        recipeDao.deleteRecipeByUuid(uuid = uuid)
    }
}