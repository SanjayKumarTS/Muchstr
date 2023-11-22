package com.example.munchstr.network

import com.example.munchstr.model.GoogleSignInToken
import com.example.munchstr.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {
    @GET("users")
    suspend fun checkUserByEmail(@Query("email") email: String): Response<User>
    @GET("users")
    suspend fun checkUserById(@Query("uuid") uuid: String): Response<User>
    @POST("users/googleSignIn")
    suspend fun googleSignIn(@Body idToken: GoogleSignInToken): Response<User>
}