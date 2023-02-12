package com.project.sws.album

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun String?.toDate(): Date? {
    if (this == null) return null

    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return try {
        formatter.parse(this)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Date?.formatToString(format: String, defaultText: String = "-"): String {
    if (this == null) return defaultText
    val formatter = SimpleDateFormat(format)
    return try {
        formatter.format(this)
    } catch (e: Exception) {
        e.printStackTrace()
        defaultText
    }
}

fun Number?.toFormatDecimal(decimal: Int, defaultText: String = "-"): String {
    if (this == null) return defaultText
    return try {
        val df = DecimalFormat("#,###")
        df.minimumFractionDigits = decimal
        df.minimumIntegerDigits = 1
        df.format(this)
    } catch (e: Exception) {
        defaultText
    }
}