package com.dvt.weatherapp.common.util

import com.dvt.weatherapp.common.enums.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtil {

    companion object {

        fun format(input: Long, dateFormat: DateFormat): String {
            val calendar = buildCalendarFromTimesInMills(input)
            val format = SimpleDateFormat(dateFormat.format, Locale.getDefault())

            return format.format(calendar.time)
        }

        private fun buildCalendarFromTimesInMills(input: Long): Calendar {
            val date = Date(input.toInt() * 1000L)
            val calendar = Calendar.getInstance()
            calendar.time = date

            return calendar
        }

        fun isDayInTheFuture(input: Long): Boolean {
            val targetCalendar = buildCalendarFromTimesInMills(input)

            return targetCalendar.get(Calendar.DAY_OF_MONTH) > Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        }
    }
}