package com.example.munchstr.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.munchstr.database.RecipeRepository
import com.example.munchstr.model.Recipe
import com.example.munchstr.model.ResponseFindRecipesForUserDTO
import com.example.munchstr.network.RecipeApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val apiService: 
                                          RecipeApiService,
                                          private val recipeRepository: RecipeRepository
) : ViewModel() {

    // Use LiveData or StateFlow to hold the API response
    private val _recipes = mutableStateListOf<Recipe>()
    val recipes: List<Recipe> = _recipes

    private val _recipesForCards = mutableStateListOf<ResponseFindRecipesForUserDTO>()
    val recipesForCards: List<ResponseFindRecipesForUserDTO> = _recipesForCards

    private val _savedRecipes = mutableStateListOf<Recipe>()
    val savedRecipes: List<Recipe> = _savedRecipes

    private val _selectedRecipe = mutableStateOf<Recipe?>(null)
    val selectedRecipe: State<Recipe?> = _selectedRecipe

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    val savedRecipesFlow: Flow<List<Recipe>> = recipeRepository.getAllRecipes()

    fun insertRecipeToDatabase(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.insertRecipe(recipe = recipe)
        }
    }

    fun deleteRecipeFromDatabase(uuid: String){
        viewModelScope.launch {
            recipeRepository.deleteRecipeByUuid(uuid = uuid)
        }
    }
    
    fun loadRecipesForUser(uuid: String){
        viewModelScope.launch{
            try {
                _isRefreshing.value = true
                val response = apiService.findRecipesForUser(uuid)
                if(response.isSuccessful){
                    val recipesList = response.body()
                    if (!recipesList.isNullOrEmpty()) {
                        _recipesForCards.addAll(recipesList)
                    }
                }
                else {
                    // The server responded with an error
                    val errorBody = response.errorBody()?.string()
                    val statusCode = response.code()
                    if (errorBody.isNullOrEmpty()) {
                        Log.e("loadRecipes", "Error response with status code: $statusCode")
                    } else {
                        Log.e("loadRecipes", "Error response ($statusCode): $errorBody")
                    }
                }
            }catch (e: Exception) {
                // There was an error performing the HTTP request
                Log.e("loadRecipes", "Exception occurred: ${e.message}", e)
            }
            finally {
                _isRefreshing.value = false
            }
        }
    }

    fun loadRecipes() {
        viewModelScope.launch {
            try {
                _isRefreshing.value = true
                val response = apiService.findRecipeByRecipeName()
                if (response.isSuccessful) {
                    val recipesList = response.body()?.recipes
                    if (!recipesList.isNullOrEmpty()) {
                        _recipes.addAll(recipesList)
                    }
                } else {
                    // The server responded with an error
                    val errorBody = response.errorBody()?.string()
                    val statusCode = response.code()
                    if (errorBody.isNullOrEmpty()) {
                        Log.e("loadRecipes", "Error response with status code: $statusCode")
                    } else {
                        Log.e("loadRecipes", "Error response ($statusCode): $errorBody")
                    }
                }
            } catch (e: Exception) {
                // There was an error performing the HTTP request
                Log.e("loadRecipes", "Exception occurred: ${e.message}", e)
            }
            finally {
                _isRefreshing.value = false
            }
        }
    }

    fun loadSingleRecipe(recipeId: String){
        viewModelScope.launch {
            try {
                _isRefreshing.value = true
                val response = apiService.findRecipeByRecipeID(uuid = recipeId)
                if(response.isSuccessful){
                    val recipe = response.body()?.recipes?.first()
                    if(recipe!=null){
                        _selectedRecipe.value = recipe
                    }
                }
                else{
                    val errorBody = response.errorBody()?.string()
                    val statusCode = response.code()
                    if (errorBody.isNullOrEmpty()) {
                        Log.e("loadSingleRecipe", "Error response with status code: $statusCode")
                    } else {
                        Log.e("loadSingleRecipe", "Error response ($statusCode): $errorBody")
                    }
                }
            }
            catch (e: Exception){
                Log.e("loadSingleRecipe", "Exception occurred: ${e.message}", e)
            }
            finally {
                _isRefreshing.value = false
            }
        }
    }

}
