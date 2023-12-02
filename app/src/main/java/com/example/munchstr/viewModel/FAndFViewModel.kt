package com.example.munchstr.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.munchstr.model.CreateLikeDto
import com.example.munchstr.model.FollowersAndFollowing
import com.example.munchstr.network.FollowersAndFollowingApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FAndFViewModel @Inject constructor(
    private val followersAndFollowingApiService: FollowersAndFollowingApiService
):ViewModel() {
    private val _followersAndFollowing =
        mutableStateOf<FollowersAndFollowing?>(null)
    val followersAndFollowing: State<FollowersAndFollowing?> = _followersAndFollowing

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing






    fun unfollow(userId: String, targetUserId: String){
        viewModelScope.launch{
            try {
                val response = followersAndFollowingApiService.unfollowUser(
                    userId,targetUserId
                )
                if(response.isSuccessful){
                    Log.i("UnFollowed","Following")
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



    fun addFollow(userId: String, targetUserId: String){
        viewModelScope.launch{
            try {
                val response = followersAndFollowingApiService.followUser(
                    userId,targetUserId
                )
                if(response.isSuccessful){
                    Log.i("Followed","Following")
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



    fun updateFollowersAndFollowing(uuid:String){
        viewModelScope.launch{
            try {
                _isRefreshing.value = true

                val response = followersAndFollowingApiService
                    .getFollowersAndFollowing(uuid = uuid)

                if(response.isSuccessful){
                    val followersAndFollowingData = response.body()
                    Log.d("APIResponse: ","$uuid")
                    Log.d("APIResponse", "Followers: ${response.body()?.followers}, Following: ${response.body()?.following}")
                    if(followersAndFollowingData != null){
                        _followersAndFollowing.value = followersAndFollowingData
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