package com.example.munchstr.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun formatCreationTime(creationTimeString: String): String {
    // Define two different date formats
    val format1 =
        SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH)
    val format2 =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)

    // Set time zones
    format1.timeZone = TimeZone.getTimeZone("GMT")
    format2.timeZone = TimeZone.getTimeZone("UTC")

    // Try parsing with the first format
    val creationTime: Date = try {
        format1.parse(creationTimeString)
    } catch (e: Exception) {
        null // Parsing failed, try the second format
    } ?: try {
        format2.parse(creationTimeString)
    } catch (e: Exception) {
        null // Parsing failed with both formats
    } ?: return "Invalid date"

    // Handle null (parsing failed with both formats)

    // Calculate elapsed time
    val now = Date()
    val elapsedTimeMillis = now.time - creationTime.time
    val elapsedTimeMinutes = elapsedTimeMillis / 60000
    val elapsedTimeHours = elapsedTimeMinutes / 60
    val elapsedTimeDays = elapsedTimeHours / 24

    // Format the elapsed time
    return when {
        elapsedTimeDays >= 1 -> "${elapsedTimeDays}d"
        elapsedTimeHours >= 1 -> "${elapsedTimeHours}h"
        elapsedTimeMinutes >= 1 -> "${elapsedTimeMinutes}m"
        else -> "Just now"
    }
}
