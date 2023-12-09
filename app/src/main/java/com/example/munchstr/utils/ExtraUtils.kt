package com.example.munchstr.utils

import java.util.Locale

fun String.toTitleCase(): String {
    return this.split(" ").joinToString(" ") { it.capitalize(Locale.getDefault()) }
}
fun formatTimeInMinutes(timeInMinutes: Int): String {
    val hours = timeInMinutes / 60
    val minutes = timeInMinutes % 60

    return buildString {
        if (hours > 0) {
            append("$hours Hr")
            if (minutes > 0) {
                append(" ")
            }
        }
        if (minutes > 0) {
            append("$minutes mins")
        }
    }
}
