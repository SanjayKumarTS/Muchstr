package com.example.munchstr.viewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.munchstr.model.Author
import com.example.munchstr.model.FollowersAndFollowing
import com.example.munchstr.model.GetUsersRequest
import com.example.munchstr.model.GoogleSignInToken
import com.example.munchstr.model.ResponseFindRecipesForUserDTO
import com.example.munchstr.model.SignInState
import com.example.munchstr.model.User
import com.example.munchstr.model.UserDTO
import com.example.munchstr.model.UserForProfile
import com.example.munchstr.network.AuthApiService
import com.example.munchstr.network.FollowersAndFollowingApiService
import com.example.munchstr.sharedPreferences.UserRepository
import com.example.munchstr.ui.screens.login.GoogleAuthUiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val googleAuthUiClient: GoogleAuthUiClient,
    private val authApiService: AuthApiService,
    private val userRepository: UserRepository,
    private val followersAndFollowingApiService: FollowersAndFollowingApiService
) : ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state.asStateFlow()

    private val _signOutSuccess = MutableStateFlow(false)
    val signOutSuccess: StateFlow<Boolean> = _signOutSuccess.asStateFlow()

    private val _userData = MutableStateFlow<User?>(null)
    val userData: StateFlow<User?> = _userData.asStateFlow()

    private val _selectedUserData = MutableStateFlow<UserForProfile?>(null)
    val selectedUserData: StateFlow<UserForProfile?> = _selectedUserData.asStateFlow()

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    private val _searchUserData = mutableStateListOf<UserForProfile>()
    val searchUserData: List<UserForProfile> = _searchUserData

    private val _followersFollowing = mutableStateOf<FollowersAndFollowing?>(null)
    val followersFollowing:State<FollowersAndFollowing?> = _followersFollowing

    fun loadFollowersFollowing(uuid:String){
        Log.i("loadSelectedUserData", "$uuid")
        viewModelScope.launch{
            try {
                _isRefreshing.value = true
                val response = followersAndFollowingApiService
                    .getFollowersAndFollowing(uuid = uuid)
                if(response.isSuccessful){
                    _followersFollowing.value = response.body()
                }
                else {
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

    fun loadSelectedUserData(uuid: String){
        Log.i("loadSelectedUserData", "$uuid")
        viewModelScope.launch{
            try {
                _isRefreshing.value = true
                val response = authApiService.findUsers(uuid = uuid,name =
                null, email = null)
                if(response.isSuccessful){
                    val userInfo = response.body()?.get(0)
                    _selectedUserData.value = userInfo
                    Log.i("loadSelectedUserData", "${response.body()}")
                }
                else {
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

    fun searchUsers(name: String){
        viewModelScope.launch{
            try {
                _isRefreshing.value = true
                val response = authApiService.findUsers(
                    uuid = null,name = name, email = null)
                if(response.isSuccessful){
                    val userInfos = response.body()
                    _searchUserData.clear()
                    if (userInfos != null) {
                        _searchUserData.addAll(userInfos)
                    }
                }
                else {
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

    fun signIn(activityResultLauncher: ActivityResultLauncher<IntentSenderRequest>) {
        viewModelScope.launch {
            Log.d("SignInViewModel", "Initiating sign-in process")
            try {
                val intentSender = googleAuthUiClient.signIn()
                if (intentSender != null) {
                    Log.d("SignInViewModel", "Launching intent sender")
                    val intentSenderRequest = IntentSenderRequest.Builder(intentSender).build()
                    activityResultLauncher.launch(intentSenderRequest)
                } else {
                    Log.e("SignInViewModel", "Intent sender is null")
                    _state.update { SignInState(isSignInSuccessful = false, signInError = "Failed to create sign-in intent") }
                }
            } catch (e: Exception) {
                _state.update { SignInState(isSignInSuccessful = false, signInError = e.message) }
            }
        }
    }

    fun onSignInResult(data: Intent?) {
        viewModelScope.launch {
            try {
                data?.let { it ->
                    val credential = googleAuthUiClient.getSignInCredentialFromIntent(it)
                    val idToken = credential.googleIdToken
                    if (idToken != null) {
                        val response = authApiService.googleSignIn(GoogleSignInToken(idToken))
                        if (response.isSuccessful) {
                            val userInfo: User = response.body()!!
                            _userData.value = userInfo
                            userRepository.saveUserData(userInfo)
                            _state.update { it.copy(isSignInSuccessful = true) }
                        } else {
                            _state.update { it.copy(isSignInSuccessful = false, signInError = "Backend authentication failed") }
                        }
                    } else {
                        _state.update { it.copy(isSignInSuccessful = false, signInError = "No ID token") }
                    }
                } ?: run {
                    _state.update { it.copy(isSignInSuccessful = false, signInError = "Sign-in intent data is null") }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isSignInSuccessful = false, signInError = e.message) }
            }
        }
    }

    fun checkCachedUserData():User? {
        val cachedUser = userRepository.getCachedUserData()
        if (cachedUser != null) {
            _userData.value = cachedUser
            _state.value = SignInState(isSignInSuccessful = true)
        }
        return cachedUser
    }

    private fun resetState() {
        _state.update { SignInState() }
    }

    fun signOut() {
        viewModelScope.launch {
            try {
                googleAuthUiClient.signOut()
                Log.d("SignInViewModel", "Sign-out successful")
                _signOutSuccess.value = true
                _userData.value = null
                userRepository.clearUserData()
                resetState()
            } catch (e: Exception) {
                Log.e("SignInViewModel", "Sign-out failed", e)
            }
        }
    }

    fun updateUserData(uuid: String, userDTO: UserDTO) {
        Log.i("updateUserData", "Updating user: $uuid")
        viewModelScope.launch {
            try {
                val response = authApiService.updateUser(uuid, userDTO)
                if (response.isSuccessful) {
                    Log.i("updateUserData", "User successfully updated")
                } else {
                    val errorBody = response.errorBody()?.string()
                    val statusCode = response.code()
                    Log.e("updateUserData", "Error response ($statusCode): $errorBody")
                }
            } catch (e: Exception) {
                Log.e("updateUserData", "Exception occurred: ${e.message}", e)
            }
        }
    }
    fun resetSignOutSuccess() {
        _signOutSuccess.value = false
    }

}