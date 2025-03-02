package ma.n1akai.edusync.util

import android.content.Context
import ma.n1akai.edusync.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

fun stringToDate(date: String, customFormat: String = ""): Date? {

    val format: String = customFormat.ifBlank {
        "yyyy-MM-dd HH:mm:ss"
    }
    val formater = SimpleDateFormat(format)
    try {
        return formater.parse(date)
    } catch (e: Exception) {
        println("Error parsing date time string: $e")
    }
    return null
}

fun formatToRelativeTime(date: String, context: Context) : String {
    val theDate = stringToDate(date)

    val now = Calendar.getInstance().time

    if (theDate == null)
        return context.getString(R.string.unknown_date)

    val diffInMs = now.time - theDate.time
    val diffInMin = diffInMs / 60000
    val diffInHour = diffInMin / 60
    val diffInDay = diffInHour / 24
    val diffInMonth = diffInDay / 30
    val diffInYear = diffInMonth / 12

    return when {
        diffInMin < 2 -> context.getString(R.string.just_now)
        diffInMin < 60 -> context.getString(R.string.minutes_ago, diffInMin)
        diffInHour < 24 -> context.getString(R.string.hours_ago, diffInHour)
        diffInDay < 30 -> context.getString(R.string.days_ago, diffInDay)
        diffInMonth < 12 -> context.getString(R.string.months_ago, diffInMonth)
        else -> context.getString(R.string.years_ago, diffInYear)
    }

}

fun monthName(month: Int): String {
    val cal = Calendar.getInstance()
    val month_date = SimpleDateFormat("MMMM")
    cal[Calendar.MONTH] = month
    val month_name = month_date.format(cal.time)
    return month_name.uppercase().substring(0, 3)
}

fun calculateHoursBetween(startTime: String, endTime: String): Long {
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())

    val t1 = format.parse(startTime) ?: return 0
    val t2 = format.parse(endTime) ?: return 0

    val diffInMillis = t2.time - t1.time
    return TimeUnit.MILLISECONDS.toHours(diffInMillis)
}