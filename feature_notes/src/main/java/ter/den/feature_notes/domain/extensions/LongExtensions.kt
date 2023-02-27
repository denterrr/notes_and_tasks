package ter.den.feature_notes.domain.extensions

import ter.den.feature_notes.presentation.NoteAdapter
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*


//Преобразование к виду: 47 минут назад, Вчера, 26 февраля 2021
fun Long.toDate(): Pair<Int, String> {
    val now = Date().toInstant()
    val thenDate = Date(this)
    val then = thenDate.toInstant()
    val minutes = Duration.between(then, now).toMinutes()
    return if (minutes == 0L) NoteAdapter.RIGHT_NOW to ""
    else if (minutes < 60L) NoteAdapter.MINUTES to minutes.toString()
    else {
        val hours = minutes / 60
        val days = Duration.between(then, now).toDays()
        if (days == 0L) {
            NoteAdapter.HOURS to hours.toString()
        } else {
            if (days == 1L) NoteAdapter.YESTERDAY to ""
            val calendar = Calendar.getInstance()
            calendar.roll(Calendar.DAY_OF_WEEK, 1)
            if (calendar.timeInMillis < this) {
                val format = SimpleDateFormat(DatePattern.WEEK, Locale.getDefault())
                NoteAdapter.DAY_OF_WEEK to format.format(thenDate)
            } else {
                val currentYear = calendar.get(Calendar.YEAR)
                calendar.timeInMillis = this
                val thenYear = calendar.get(Calendar.YEAR)
                val format =
                    if (currentYear == thenYear) SimpleDateFormat(
                        DatePattern.DAY_MONTH,
                        Locale.getDefault()
                    )
                    else SimpleDateFormat(DatePattern.DAY_MONTH_YEAR, Locale.getDefault())
                NoteAdapter.FULL_DATE to format.format(thenDate)
            }
        }
    }
}

object DatePattern {
    const val WEEK = "EEEE"
    const val DAY_MONTH = "d MMMM"
    const val DAY_MONTH_YEAR = "d MMMM yyyy"
}