package com.example.munchstr.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [RecipeEntity::class, AuthorEntity::class], version = 1,
exportSchema =
false)
@TypeConverters(Converters::class)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun AuthorDao(): AuthorDao
}