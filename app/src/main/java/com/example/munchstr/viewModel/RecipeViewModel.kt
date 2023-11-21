package com.example.munchstr.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.example.munchstr.model.Recipe
import com.example.munchstr.network.RecipeApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val apiService: RecipeApiService) : ViewModel() {

    // Use LiveData or StateFlow to hold the API response
    private val _recipes = mutableStateListOf<Recipe>()
    val recipes: List<Recipe> = _recipes

    private val _selectedRecipe = mutableStateOf<Recipe?>(null)
    val selectedRecipe: State<Recipe?> = _selectedRecipe

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

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
