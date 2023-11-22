package com.example.munchstr.sharedPreferences

import android.content.Context
import com.example.munchstr.model.User
import com.google.gson.Gson

class UserRepository(private val context: Context) {
    private val gson = Gson()

    fun saveUserData(user: User) {
        val sharedPref = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val userDataJson = gson.toJson(user)
        with(sharedPref.edit()) {
            putString("userData", userDataJson)
            apply()
        }
    }

    fun getCachedUserData(): User? {
        val sharedPref = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val userDataJson = sharedPref.getString("userData", null)
        return if (userDataJson != null) {
            gson.fromJson(userDataJson, User::class.java)
        } else {
            null
        }
    }

    fun clearUserData() {
        val sharedPref = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            remove("userData")
            apply()
        }
    }
}
