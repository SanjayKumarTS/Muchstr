package com.example.munchstr.database

import androidx.room.TypeConverter
import com.example.munchstr.model.Author
import com.example.munchstr.model.Ingredient
import com.example.munchstr.model.Nutrition
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()
    @TypeConverter
    fun fromIngredientsList(ingredients: List<Ingredient>): String {
        return gson.toJson(ingredients)
    }

    @TypeConverter
    fun toIngredientsList(ingredientsString: String): List<Ingredient> {
        val type = object : TypeToken<List<Ingredient>>() {}.type
        return gson.fromJson(ingredientsString, type)
    }

    @TypeConverter
    fun fromNutritionList(nutrition: List<Nutrition>): String {
        return gson.toJson(nutrition)
    }

    @TypeConverter
    fun toNutritionList(nutritionString: String): List<Nutrition> {
        val type = object : TypeToken<List<Nutrition>>() {}.type
        return gson.fromJson(nutritionString, type)
    }

    @TypeConverter
    fun fromStringList(strings: List<String>): String {
        return gson.toJson(strings)
    }

    @TypeConverter
    fun toStringList(string: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun toAuthor(authorString: String): Author {
        val type = object : TypeToken<Author>() {}.type
        return gson.fromJson(authorString, type)
    }

    @TypeConverter
    fun fromAuthor(author: Author): String {
        return gson.toJson(author)
    }

    @TypeConverter
    fun toAuthorEntity(authorEntityString: String): AuthorEntity {
        val type = object : TypeToken<AuthorEntity>() {}.type
        return gson.fromJson(authorEntityString, type)
    }

    @TypeConverter
    fun fromAuthorEntity(authorEntity: AuthorEntity): String {
        return gson.toJson(authorEntity)
    }
}