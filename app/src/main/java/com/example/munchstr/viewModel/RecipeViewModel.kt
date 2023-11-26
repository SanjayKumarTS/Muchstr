package com.example.munchstr.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.munchstr.database.AuthorRepository
import com.example.munchstr.database.RecipeRepository
import com.example.munchstr.model.Author
import com.example.munchstr.model.CreateLikeDto
import com.example.munchstr.model.PostRecipe
import com.example.munchstr.model.Recipe
import com.example.munchstr.model.ResponseFindRecipesForUserDTO
import com.example.munchstr.network.RecipeApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val apiService: 
                                          RecipeApiService,
                                          private val recipeRepository:
                                          RecipeRepository,
    private val authorRepository: AuthorRepository
) : ViewModel() {

    // Use LiveData or StateFlow to hold the API response
    private val _recipes = mutableStateListOf<Recipe>()
    val recipes: List<Recipe> = _recipes

    private val _recipesForCards = mutableStateListOf<ResponseFindRecipesForUserDTO>()
    val recipesForCards: List<ResponseFindRecipesForUserDTO> = _recipesForCards

    private val _savedRecipes = mutableStateListOf<Recipe>()
    val savedRecipes: List<Recipe> = _savedRecipes

    private val _savedAuthors = mutableStateListOf<Author>()
    val savedAuthors: List<Author> = _savedAuthors

    private val _selectedRecipe = mutableStateOf<Recipe?>(null)
    val selectedRecipe: State<Recipe?> = _selectedRecipe

    private val _selectedRecipeAuthor = mutableStateOf<Author?>(null)
    val selectedRecipeAuthor: State<Author?> = _selectedRecipeAuthor

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    val savedRecipesFlow: Flow<List<Recipe>> = recipeRepository.getAllRecipes()
    val savedAuthorsFlow: Flow<List<Author>> = authorRepository.getAllAuthors()


    private val _imageUrl = mutableStateOf<String?>(null)
    val imageUrl: State<String?> = _imageUrl

    val navigateToHome = MutableStateFlow(false)

    fun searchRecipes(name: String) {
        viewModelScope.launch {
            try {
                val response = apiService.searchRecipe(name)
                if (response.isSuccessful) {
                    // Update the LiveData with the search results
                    _recipesForCards.clear()
                    response.body()?.let { _recipesForCards.addAll(it) }
                } else {
                    // Handle API error
                    Log.e("RecipeViewModel", "Error during search: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                // Handle exceptions
                Log.e("RecipeViewModel", "Exception during search: ${e.message}")
            }
        }
    }

    fun addLike(recipeId: String, authorId: String){
        viewModelScope.launch{
            try {
                val response = apiService.createLike(CreateLikeDto(
                    recipeId = recipeId,
                    userId = authorId
                ))
                if(response.isSuccessful){
                    Log.i("Liked","Recipe Liked")
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
        }
    }

    fun searchRecipe(name:String){
        viewModelScope.launch{
            try {
                _isRefreshing.value = true
                val response = apiService.findRecipeByRecipeName(name = name)
                if(response.isSuccessful){
                    val recipesList = response.body()
                    if (!recipesList.isNullOrEmpty()) {
                        _recipesForCards.clear()
                        Log.d("SearchPage", "Refetched List: ${recipesList
                            .size}")
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

    fun setImageUrl(url: String?) {
        _imageUrl.value = url
    }


    fun insertRecipeToDatabase(recipe: Recipe, author: Author) {
        viewModelScope.launch {
            recipeRepository.insertRecipe(recipe = recipe)
            authorRepository.insertAuthor(author = author)
        }
    }

    fun deleteRecipeFromDatabase(uuid: String){
        viewModelScope.launch {
            recipeRepository.deleteRecipeByUuid(uuid = uuid)
            checkAndDeleteRecipeFromDatabase()
        }
    }

    private fun checkAndDeleteRecipeFromDatabase(){
        viewModelScope.launch {
            val allRecipes = savedRecipesFlow.first()
            val allAuthors = savedAuthorsFlow.first()
            val referencedAuthorUuids = allRecipes.map { it.authorId }.toSet()

            // Find authors not referenced by any recipe
            val unreferencedAuthors = allAuthors.filter { it.uuid !in referencedAuthorUuids }

            // Delete unreferenced authors
            unreferencedAuthors.forEach { author ->
                authorRepository.deleteAuthor(author)
            }
        }
    }

    fun loadRecipesOfUser(authorId: String){
        viewModelScope.launch{
            try {
                _isRefreshing.value = true
                val response = apiService.findRecipesOfUser(authorId = authorId)
                if(response.isSuccessful){
                    val recipesList = response.body()
                    if (!recipesList.isNullOrEmpty()) {
                        _recipesForCards.clear()
                        Log.d("SearchPage", "Refetched List: ${recipesList
                            .size}")
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

    fun postRecipe(newRecipe: PostRecipe) {
        viewModelScope.launch {
            try {
                val response = apiService.createRecipe(newRecipe)
                if (response.isSuccessful) {
                    navigateToHome.value = true

                    Log.i("postRecipe", "Recipe posted successfully.")
                } else {
                    val errorBody = response.errorBody()?.string()
                    val statusCode = response.code()
                    if (errorBody.isNullOrEmpty()) {
                        Log.e("postRecipe", "Error response with status code: $statusCode")
                    } else {
                        Log.e("postRecipe", "Error response ($statusCode): $errorBody")
                    }
                }
            } catch (e: Exception) {
                Log.e("postRecipe", "Exception occurred: ${e.message}", e)
            } finally {
                System.out.println("Finally Executed")
            }
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
                        _recipesForCards.clear()
                        Log.d("HomePage", "Refetched List: ${recipesList.size}")
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

//    fun loadRecipes() {
//        viewModelScope.launch {
//            try {
//                _isRefreshing.value = true
//                val response = apiService.findRecipeByRecipeName()
//                if (response.isSuccessful) {
//                    val recipesList = response.body()?.recipes
//                    if (!recipesList.isNullOrEmpty()) {
//                        _recipes.addAll(recipesList)
//                    }
//                } else {
//                    // The server responded with an error
//                    val errorBody = response.errorBody()?.string()
//                    val statusCode = response.code()
//                    if (errorBody.isNullOrEmpty()) {
//                        Log.e("loadRecipes", "Error response with status code: $statusCode")
//                    } else {
//                        Log.e("loadRecipes", "Error response ($statusCode): $errorBody")
//                    }
//                }
//            } catch (e: Exception) {
//                // There was an error performing the HTTP request
//                Log.e("loadRecipes", "Exception occurred: ${e.message}", e)
//            }
//            finally {
//                _isRefreshing.value = false
//            }
//        }
//    }

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

    fun updateSelectedRecipeAuthor(author: Author) {
        _selectedRecipeAuthor.value = author
    }

}
