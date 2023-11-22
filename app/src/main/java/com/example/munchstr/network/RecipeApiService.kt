package com.example.munchstr.network

import com.example.munchstr.model.Recipe
import com.example.munchstr.model.RecipeResponse
import com.example.munchstr.model.ResponseFindRecipesForUserDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {
    @GET("recipe")
    suspend fun findRecipeByRecipeName(
        @Query("name") name: String = "",
        @Query("page") page: String = ""
    ): Response<RecipeResponse>

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
}
