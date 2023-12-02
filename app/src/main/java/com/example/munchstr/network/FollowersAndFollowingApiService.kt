package com.example.munchstr.network

import com.example.munchstr.model.FollowersAndFollowing
import com.example.munchstr.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FollowersAndFollowingApiService {
    @GET("followers-following/followersAndFollowing/{uuid}")
    suspend fun getFollowersAndFollowing(@Path("uuid") uuid: String):
            Response<FollowersAndFollowing>

    @POST("followers-following/{userId}/follow/{targetUserId}")
    suspend fun followUser(
        @Path("userId") userId: String,
        @Path("targetUserId") targetUserId: String
    ): Response<Unit>

    @POST("followers-following/{userId}/unfollow/{targetUserId}")
    suspend fun unfollowUser(
        @Path("userId") userId: String,
        @Path("targetUserId") targetUserId: String
    ): Response<Unit>


}