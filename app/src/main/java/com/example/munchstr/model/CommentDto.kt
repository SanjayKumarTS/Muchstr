package com.example.munchstr.model

data class CommentDto(
    val comment:String,
    val userInfo: UserForProfile
)
data class LikeCommentResponse(
    val _id: String,
    val recipeId: String,
    val comments: List<Comment>,
    val createdAt: String,
    val likes: List<String>,
    val updatedAt: String
)
data class GetAllLikeCommentResponse(
    val _id: String,
    val recipeId: String,
    val comments: List<Comment> = emptyList(),
    val createdAt: String,
    val likes: List<String>,
    val updatedAt: String
)

data class Comment(

    val id: String,
    val text: String,
)