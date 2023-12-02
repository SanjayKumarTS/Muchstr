package com.example.munchstr.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.munchstr.model.CommentDto
import com.example.munchstr.model.PostComment
import com.example.munchstr.network.CommentApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val commentApiService: CommentApiService
):ViewModel() {
    private val _comments = mutableStateListOf<CommentDto?>(null)
    val comments: List<CommentDto?> = _comments

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    fun postComment(postComment: PostComment){
        viewModelScope.launch{
            try {
                _isRefreshing.value = true
                val response = commentApiService.postComments(postComment = postComment)
                if(response.isSuccessful){
                    updateComments(postComment.recipeId)
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

    fun updateComments(recipeId: String){
        viewModelScope.launch{
            try {
                _isRefreshing.value = true
                val response = commentApiService.getComments(uuid = recipeId)
                if(response.isSuccessful){
                    val commentsList = response.body()
                    if(commentsList != null){
                        _comments.clear()
                        _comments.addAll(commentsList)
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
}