package com.example.munchstr.network

import com.example.munchstr.model.CommentDto
import com.example.munchstr.model.FollowersAndFollowing
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CommentApiService {
    @GET("like-comment/comments/{uuid}")
    suspend fun getComments(@Path("uuid") uuid: String):
            Response<List<CommentDto>>
}