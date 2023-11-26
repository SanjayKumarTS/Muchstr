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
object AppDatabaseModule{
    @Provides
    fun provideRecipeDatabase(@ApplicationContext context: Context):
            AppRoomDatabase {
        return Room.databaseBuilder(
            context,
            AppRoomDatabase::class.java, "app-database"
        ).build()
    }

    @Provides
    fun provideRecipeDao(database: AppRoomDatabase) = database.recipeDao()

    @Provides
    fun provideAuthorDao(database: AppRoomDatabase) = database.AuthorDao()

    @Provides
    fun provideRecipeRepository(dao: RecipeDao): RecipeRepository {
        return RecipeRepositoryImpl(dao)
    }
    @Provides
    fun provideAuthorRepository(authorDao: AuthorDao): AuthorRepository {
        return AuthorRepositoryImpl(authorDao)
    }
}