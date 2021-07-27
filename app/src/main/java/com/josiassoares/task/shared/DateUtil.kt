package com.josias.soares.task.shared

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    class DateDeserializer : JsonDeserializer<Date> {
        @Throws(JsonParseException::class)
        override fun deserialize(
            element: JsonElement,
            arg1: Type,
            arg2: JsonDeserializationContext
        ): Date? {
            val lDate = element.asString
            val lFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
            return try {
                lFormat.parse(lDate)
            } catch (exp: ParseException) {
                val datereip = lDate.replace("/Date(", "").replace(")/", "")
                val timeInMillis = java.lang.Long.valueOf(datereip)
                val lCalendar = Calendar.getInstance()
                lCalendar.timeInMillis = timeInMillis
                var startTimeMillis =
                    lCalendar[Calendar.HOUR_OF_DAY] * 1000 * 60 * 60 + (lCalendar[Calendar.MINUTE] * 1000 * 60).toLong()
                startTimeMillis -= TimeZone
                    .getDefault()
                    .getOffset(startTimeMillis)
                    .toLong()

                Date(timeInMillis)
            }
        }
    }
}