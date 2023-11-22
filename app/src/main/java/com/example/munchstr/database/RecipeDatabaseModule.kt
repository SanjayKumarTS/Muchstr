package com.example.munchstr.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object RecipeDatabaseModule{
    @Provides
    fun provideRecipeDatabase(@ApplicationContext context: Context):
            RecipeRoomDatabase {
        return Room.databaseBuilder(
            context,
            RecipeRoomDatabase::class.java, "recipe-database"
        ).build()
    }

    @Provides
    fun provideRecipeDao(database: RecipeRoomDatabase) = database.recipeDao()

    @Provides
    fun provideRecipeRepository(dao: RecipeDao): RecipeRepository {
        return RecipeRepositoryImpl(dao)
    }
}