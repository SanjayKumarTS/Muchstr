package com.example.munchstr.network

import com.example.munchstr.model.CreateLikeDto
import com.example.munchstr.model.PostRecipe
import com.example.munchstr.model.Recipe
import com.example.munchstr.model.RecipeResponse
import com.example.munchstr.model.ResponseFindRecipesForUserDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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
}
