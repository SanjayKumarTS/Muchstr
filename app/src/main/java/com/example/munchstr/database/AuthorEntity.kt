package com.example.munchstr.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authors")
data class AuthorEntity(
    @PrimaryKey val uuid: String,
    val name: String,
    val photo: String
)

