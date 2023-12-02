package com.example.munchstr.network

import com.example.munchstr.model.CommentDto
import com.example.munchstr.model.FollowersAndFollowing
import com.example.munchstr.model.PostComment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentApiService {
    @GET("like-comment/comments/{uuid}")
    suspend fun getComments(@Path("uuid") uuid: String):
            Response<List<CommentDto>>

    @POST("like-comment/comment")
    suspend fun postComments(@Body postComment: PostComment):Response<Unit>
}