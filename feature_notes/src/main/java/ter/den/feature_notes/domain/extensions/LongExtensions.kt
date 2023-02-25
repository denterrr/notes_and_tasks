package ter.den.feature_notes.domain.extensions

import ter.den.feature_notes.presentation.NoteAdapter
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*

fun Long.toDate(): Pair<Int, String> {
    val now = Date().toInstant()
    val thenDate = Date(this)
    val then = thenDate.toInstant()
    val minutes = Duration.between(then, now).toMinutes()
    if (minutes == 0L) return NoteAdapter.RIGHT_NOW to ""
    else if (minutes == 1L) return NoteAdapter.MINUTE_AGO to ""
    else if (minutes < 60L) return NoteAdapter.ANY_MINUTES_AGO to minutes.toString()
    else {
        val hours = minutes / 60
        val days = Duration.between(then, now).toDays()
        if (days == 0L) {
            return if (hours == 1L) NoteAdapter.HOUR_AGO to ""
            else NoteAdapter.ANY_HOURS_AGO to hours.toString()
        } else {
            if (days == 1L) return NoteAdapter.YESTERDAY to ""
            val calendar = Calendar.getInstance()
            calendar.roll(Calendar.DAY_OF_WEEK, 1)
            return if (calendar.timeInMillis < this) {
                val format = SimpleDateFormat("EEEE", Locale.getDefault())
                NoteAdapter.DAY_OF_WEEK to format.format(thenDate)
            } else {
                val currentYear = calendar.get(Calendar.YEAR)
                calendar.timeInMillis = this
                val thenYear = calendar.get(Calendar.YEAR)
                val format =
                    if (currentYear == thenYear) SimpleDateFormat("d MMMM", Locale.getDefault())
                    else SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
                return NoteAdapter.JUST_DATE to format.format(thenDate)
            }
        }
    }
}