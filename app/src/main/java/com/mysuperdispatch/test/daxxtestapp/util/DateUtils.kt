package com.mysuperdispatch.test.daxxtestapp.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private const val TEMPLATE = "d MMM yyyy HH:mm:ss"

    fun convertToStringDate(timestamp: Long): String {
        return convertToString(Date(timestamp))
    }

    private fun convertToString(date: Date): String {
        val format = SimpleDateFormat(TEMPLATE, Locale.getDefault())
        return format.format(date)
    }

}