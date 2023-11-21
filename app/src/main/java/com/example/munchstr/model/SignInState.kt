package com.example.munchstr.model

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
