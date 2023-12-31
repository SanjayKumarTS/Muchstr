package com.example.munchstr.network

import com.example.munchstr.model.CreateLikeDto
import com.example.munchstr.model.GetAllLikeCommentResponse
import com.example.munchstr.model.LikeCommentResponse
import com.example.munchstr.model.PostRecipe
import com.example.munchstr.model.RecipeResponse
import com.example.munchstr.model.ResponseFindRecipesForUserDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApiService {
    @GET("recipe")
    suspend fun findRecipeByRecipeName(
        @Query("name") name: String = "",
        @Query("page") page: String = ""
    ): Response<List<ResponseFindRecipesForUserDTO>>

    @GET("recipe")
    suspend fun findRecipeByAuthorID(
        @Query("authorId") authorId: String = "",
        @Query("page") page: String = ""
    ): Response<RecipeResponse>

    @GET("recipe")
    suspend fun findRecipeByRecipeID(
        @Query("uuid") uuid: String = "",
        @Query("page") page: String = ""
    ): Response<RecipeResponse>

    @GET("recipe/findRecipesForUser")
    suspend fun findRecipesForUser(
        @Query("uuid") uuid: String,
        @Query("page") page: Number = 1,
        @Query("pageSize") pageSize: Number = 10
    ): Response<List<ResponseFindRecipesForUserDTO>>
    @POST("recipe")
    suspend fun createRecipe(
        @Body recipeData: PostRecipe
    ): Response<RecipeResponse>

    @GET("recipe/findRecipesOfUser")
    suspend fun findRecipesOfUser(
        @Query("authorId") authorId: String,
        @Query("page") page: Number = 1,
    ): Response<List<ResponseFindRecipesForUserDTO>>

    @POST("like-comment/like")
    suspend fun createLike(
        @Body createLikeDto: CreateLikeDto
    ):Response<Any>

    @GET("recipe/searchRecipe/{name}")
    suspend fun searchRecipe(@Path("name") name: String): Response<List<ResponseFindRecipesForUserDTO>>
    @GET("recipe/searchRecipeByCategory/{name}")
    suspend fun searchRecipeByCategory(@Path("name") name: String): Response<List<ResponseFindRecipesForUserDTO>>

    @HTTP(method = "DELETE", path = "like-comment/like", hasBody = true)
    suspend fun removeLike(
        @Body createLikeDto: CreateLikeDto
    ): Response<Any>
    @GET("like-comment/liked/{recipeId}")
    suspend fun getLikes(
        @Path("recipeId") recipeId: String
    ): Response<LikeCommentResponse>
    @GET("like-comment/likes")
    suspend fun getAllLikes(): Response<List<GetAllLikeCommentResponse>>
    @DELETE("recipe/{id}")
    suspend fun deleteRecipe(@Path("id") recipeId: String): Response<Unit>
}