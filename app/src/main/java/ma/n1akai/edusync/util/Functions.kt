package ma.n1akai.edusync.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

fun stringToDate(date: String): Date? {

    val formater = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    try {
        return formater.parse(date)
    } catch (e: Exception) {
        println("Error parsing date time string: $e")
    }
    return null
}

fun formatToRelativeTime(date: String) : String {
    val theDate = stringToDate(date)

    val now = Calendar.getInstance().time

    if (theDate == null)
        return "Unknown date"

    val diffInMs = now.time - theDate.time
    val diffInMin = diffInMs / 60000
    val diffInHour = diffInMin / 60
    val diffInDay = diffInHour / 24
    val diffInMonth = diffInDay / 30
    val diffInYear = diffInMonth / 12

    return when {
        diffInMin < 1 -> "Just now"
        diffInMin < 60 -> "$diffInMin minutes ago"
        diffInHour < 24 -> "$diffInHour hours ago"
        diffInDay < 30 -> "$diffInDay days ago"
        diffInMonth < 12 -> "$diffInMonth months ago"
        else -> "$diffInYear years ago"
    }

}