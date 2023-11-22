package com.example.munchstr.model

data class User(
    val uuid: String,
    val name: String,
    val email: String,
    val photoURL: String?,
    val tags: List<String>?,
    val favorites: List<String>?,
    val bio: String?
)