package com.example.munchstr.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.ParseException
import java.time.*
import java.time.temporal.ChronoUnit
import java.text.SimpleDateFormat
import java.util.Date


@RequiresApi(Build.VERSION_CODES.O)
fun formatCreationTime(creationTimeString: String): String {
    val dateFormat = SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z")
    val creationTime: Date? = try {
        dateFormat.parse(creationTimeString)
    } catch (e: ParseException) {
        null
    }

    if (creationTime != null) {
        val now = Instant.now()
        val elapsedTime = Duration.between(creationTime.toInstant(), now)

        if (elapsedTime.toDays() > 0) {
            val days = elapsedTime.toDays()
            return if (days.toInt() == 1) {
                "1 day ago"
            } else {
                "$days days ago"
            }
        } else if (elapsedTime.toHours() > 0) {
            val hours = elapsedTime.toHours()
            return if (hours.toInt() == 1) {
                "1 hour ago"
            } else {
                "$hours hours ago"
            }
        } else if (elapsedTime.toMinutes() > 0) {
            val minutes = elapsedTime.toMinutes()
            return if (minutes.toInt() == 1) {
                "1 minute ago"
            } else {
                "$minutes minutes ago"
            }
        } else {
            return "Just now"
        }
    } else {
        return "Invalid creation time format"
    }
}
