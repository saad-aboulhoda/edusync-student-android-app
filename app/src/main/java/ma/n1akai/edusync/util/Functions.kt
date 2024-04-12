package ma.n1akai.edusync.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

fun stringToDate(date: String, customFormat: String = ""): Date? {

    var format = ""
    if (customFormat.isBlank()) {
        format = "yyyy-MM-dd HH:mm:ss"
    } else {
        format = customFormat
    }
    val formater = SimpleDateFormat(format)
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

fun monthName(month: Int): String {
    val cal = Calendar.getInstance()
    val month_date = SimpleDateFormat("MMMM")
    cal[Calendar.MONTH] = month
    val month_name = month_date.format(cal.time)
    return month_name.uppercase().substring(0, 3)
}