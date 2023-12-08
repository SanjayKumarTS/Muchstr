package com.example.munchstr.database

import com.example.munchstr.model.Author
import com.example.munchstr.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepositoryImpl(private val recipeDao: RecipeDao) : RecipeRepository {
    override fun getAllRecipes(): Flow<List<Recipe>> {
        return recipeDao.getAllRecipes().map { entities ->
            entities.map { entity ->
                Recipe(
                    entity.uuid,
                    entity.authorId,
                    entity.name,
                    entity.description,
                    entity.ingredients,
                    entity.instructions,
                    entity.nutrition,
                    entity.preparationTime,
                    entity.cookTime,
                    entity.photo,
                    entity.tags,
                    entity.createdAt
                )
            }
        }
    }

    override suspend fun getRecipeByUuid(uuid: String): Recipe {
        return recipeDao.getRecipeByUuid(uuid = uuid)
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
            instructions = recipe.instructions,
            createdAt = recipe.createdAt
        ))
    }
    override suspend fun deleteRecipeByUuid(uuid: String) {
        recipeDao.deleteRecipeByUuid(uuid = uuid)
    }
}

class AuthorRepositoryImpl @Inject constructor(private val authorDao:
                                               AuthorDao) :
    AuthorRepository {
    override suspend fun insertAuthor(author: Author) {
        authorDao.insertAuthor(
            AuthorEntity(
            name = author.name,
            uuid = author.uuid,
            photo = author.photo
        )
        )
    }
    override suspend fun deleteAuthor(author: Author) {
        authorDao.deleteAuthor(AuthorEntity(
            name = author.name,
            uuid = author.uuid,
            photo = author.photo
        ))
    }
    override fun getAllAuthors(): Flow<List<Author>> {
        return authorDao.getAllAuthors().map { entities->
            entities.map {entity->
                Author(
                    entity.uuid, entity.name,entity.photo
                )
            }
        }
    }
}