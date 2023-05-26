package com.mhmd.core.util

import com.mhmd.constants.DateConstants.DATE_FORMAT
import com.mhmd.constants.DateConstants.DATE_TIME_FORMAT
import com.mhmd.constants.DateConstants.SERVER_DATE_FORMAT
import com.mhmd.constants.DateConstants.SERVER_DATE_TIME_FORMAT
import com.mhmd.constants.DateConstants.TIME_FORMAT
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


@Suppress("NewApi")
fun String.toLocalDate(): LocalDate {
    val inputFormat = DateTimeFormatter.ofPattern(SERVER_DATE_FORMAT, Locale.US)

    return LocalDate.parse(this, inputFormat)
}

@Suppress("NewApi")
fun String.toLocalDateTime(): LocalDateTime {
    return ZonedDateTime.parse(this).toLocalDateTime().plusHours(3)
}

fun getTranslatedDateTime(localDateTime: LocalDateTime): String {
    val local = Locale.US

    val outputFormat: java.text.DateFormat = SimpleDateFormat(DATE_TIME_FORMAT, local)

    val inputFormat: java.text.DateFormat = SimpleDateFormat(SERVER_DATE_TIME_FORMAT, Locale.US)

    return try {
        val date = inputFormat.parse(localDateTime.toString())
        if (date != null) {
            outputFormat.format(date)
        } else {
            ""
        }
    } catch (e: Exception) {
        ""
    }
}

fun getTranslatedDate(localDate: LocalDate): String {
    val local = Locale.US
    val outputFormat: java.text.DateFormat = SimpleDateFormat(DATE_FORMAT, local)
    val inputFormat: java.text.DateFormat = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.US)

    return try {
        val date = inputFormat.parse(localDate.toString())
        if (date != null) {
            outputFormat.format(date)
        } else {
            ""
        }
    } catch (e: Exception) {
        ""
    }
}

fun getTranslatedDateWithMonthName(localDate: LocalDate): String {
    val local = Locale.US
    val outputFormat: java.text.DateFormat = SimpleDateFormat("dd MMM yyyy", local)
    val inputFormat: java.text.DateFormat = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.US)

    return try {
        val date = inputFormat.parse(localDate.toString())
        if (date != null) {
            outputFormat.format(date)
        } else {
            ""
        }
    } catch (e: Exception) {
        ""
    }
}

fun getTranslatedDateWithMonthNameAndDayName(
    localDate: LocalDate
): String {
    val local = Locale.US
    val outputFormat: java.text.DateFormat = SimpleDateFormat("EEEE, dd MMM yyyy", local)
    val inputFormat: java.text.DateFormat = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.US)

    return try {
        val date = inputFormat.parse(localDate.toString())
        if (date != null) {
            outputFormat.format(date)
        } else {
            ""
        }
    } catch (e: Exception) {
        ""
    }
}

fun getTranslatedDateWithMonthNameWithoutYear(
    localDate: LocalDate
): String {
    val local = Locale.US
    val outputFormat: java.text.DateFormat = SimpleDateFormat("dd MMM", local)
    val inputFormat: java.text.DateFormat = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.US)

    return try {
        val date = inputFormat.parse(localDate.toString())
        if (date != null) {
            outputFormat.format(date)
        } else {
            ""
        }
    } catch (e: Exception) {
        ""
    }
}

fun getTranslatedDateWithDay(localDate: LocalDate): String {
    val local = Locale.US
    val outputFormat: java.text.DateFormat = SimpleDateFormat("dd", local)
    val inputFormat: java.text.DateFormat = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.US)

    return try {
        val date = inputFormat.parse(localDate.toString())
        if (date != null) {
            outputFormat.format(date)
        } else {
            ""
        }
    } catch (e: Exception) {
        ""
    }
}

fun getTranslatedDateWithPattern(
    pattern: String,
    localDate: LocalDate
): String {
    val local = Locale.US
    val outputFormat: java.text.DateFormat = SimpleDateFormat(pattern, local)
    val inputFormat: java.text.DateFormat = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.US)

    return try {
        val date = inputFormat.parse(localDate.toString())
        if (date != null) {
            outputFormat.format(date)
        } else {
            ""
        }
    } catch (e: Exception) {
        ""
    }
}

fun getTranslatedTime(localTime: LocalTime): String {
    val local = Locale.US
    val outputFormat: java.text.DateFormat = SimpleDateFormat(TIME_FORMAT, local)
    val inputFormat: java.text.DateFormat = SimpleDateFormat("HH:mm", Locale.US)

    return try {
        val date = inputFormat.parse(localTime.toString())
        if (date != null) {
            outputFormat.format(date)
        } else {
            ""
        }
    } catch (e: Exception) {
        ""
    }
}

@Suppress("NewApi")
fun Long.convertMillisToZonedDate(): LocalDate {
    return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
}

@Suppress("NewApi")
fun LocalDate.convertZonedToDateMillis(): Long {
    return this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

@Suppress("NewApi")
fun Long.convertMillisToZonedDateTime(): LocalDateTime {
    return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDateTime()
}

@Suppress("NewApi")
fun LocalDateTime.convertZonedToDateTimeMillis(): Long {
    return this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}