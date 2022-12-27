package view.common

import java.util.Date
import java.util.concurrent.TimeUnit

object ElapsedTimeCalculator {
    fun calc(startDate: Date, currentDate: Date): String {
        val totalMillis = currentDate.time - startDate.time
        val hour = TimeUnit.MILLISECONDS.toHours(totalMillis)
        val minute = TimeUnit.MILLISECONDS.toMinutes(totalMillis) % 60
        val second = TimeUnit.MILLISECONDS.toSeconds(totalMillis) % 60 % 60
        val hourString = hour.toString().padStart(2, '0')
        val minuteString = minute.toString().padStart(2, '0')
        val secondString = second.toString().padStart(2, '0')
        return "$hourString:$minuteString:$secondString"
    }
}