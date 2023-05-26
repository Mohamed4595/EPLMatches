package com.mhmd.components.utils


import android.content.Context
import com.mhmd.components.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


fun Context.minutesToHoursMinutes(timeInMinutes: Long): String {
    return timeInMinutes.run {
        val hours = this / 60
        val minutes = this % 60

        val result: StringBuilder = java.lang.StringBuilder()

        if (hours > 0) {
            result.append(getString(R.string.hour_char, hours))
            if (minutes > 0) {
                result.append(" : ")
            }
        }
        if (minutes > 0) result.append(getString(R.string.minute_char, minutes))
        result.toString()
    }
}

fun Context.secondsToHoursMinutesSeconds(timeInSeconds: Long): String {
    return timeInSeconds.run {
        val hours = this / 60 / 60
        val minutes = this / 60 % 60
        val seconds = this % 60

        val result: StringBuilder = java.lang.StringBuilder()

        result.append(getString(R.string.hour_char, hours))
        result.append(" : ")
        result.append(getString(R.string.minute_char, minutes))
        result.append(" : ")
        result.append(getString(R.string.second_char, seconds))

        result.toString()
    }
}

fun Context.minutesToDaysHoursMinutes(timeInMinutes: Long): String {
    return timeInMinutes.run {
        val days = this / 24 / 60
        val hours = this / 60 % 24
        val minutes = this % 60

        val result: StringBuilder = java.lang.StringBuilder()

        if (days > 0) {
            result.append(getString(R.string.day_char, days))
            if (hours > 0 || minutes > 0) {
                result.append(" , ")
            }
        }

        if (hours > 0) {
            result.append(getString(R.string.hour_char, hours))
            if (minutes > 0) {
                result.append(" : ")
            }
        }
        if (minutes > 0) result.append(getString(R.string.minute_char, minutes))
        result.toString()
    }
}

fun Context.convertTimeStampToDateTime(timeStamp: Date): String {
    val now = Calendar.getInstance()

    val date = Calendar.getInstance()
    date.timeInMillis = timeStamp.time

    val locale = Locale.US

    return if (now.get(Calendar.DATE) == date.get(Calendar.DATE)) {
        SimpleDateFormat("hh:mm aa", locale).format(timeStamp)
    } else {
        SimpleDateFormat("dd-MM-yyy hh:mm aa", locale).format(timeStamp)
    }
}

fun Context.getPrettyDate(timeStamp: Date, addTimeToYesterday: Boolean): String {
    val now = Calendar.getInstance()

    val date = Calendar.getInstance()
    date.timeInMillis = timeStamp.time

    val locale = Locale.US

    return if (now.get(Calendar.DATE) == date.get(Calendar.DATE)) {
        SimpleDateFormat("h:mm a", locale).format(timeStamp)
    } else if (now.get(Calendar.DATE) - date.get(Calendar.DATE) == 1) {
        if (addTimeToYesterday) {
            getString(R.string.yesterday_at) + SimpleDateFormat("h:mm a", locale).format(
                timeStamp
            )
        } else {
            getString(R.string.yesterday)
        }
    } else {
        SimpleDateFormat("d/M/yy", locale).format(timeStamp)
    }
}
