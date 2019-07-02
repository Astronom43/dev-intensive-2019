package ru.skillbranch.devintensive.extensions

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = SECOND * 60
const val HOUR = MINUTE * 60
const val DAY = HOUR * 24

const val TAG = "DATE"

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time;
    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}

fun Date.humanizeDiff(): String {
    val now = Date()
    val deltaTime = (now.time/1000 - this.time/1000)

    var rez = when (Math.abs(deltaTime)) {
        in (0..0) -> "только что"
        in (1..44) -> "несколько секунд"
        in (45..74) -> "минуту"
        in (75..45 * 60-1) -> DeltaTimeToString(deltaTime / 60, TimeUnits.MINUTE)
        in (45 * 60..75 * 60-1) -> "час"
        in (75 * 60..22 * 60 * 60-1) ->DeltaTimeToString(deltaTime / (60 * 60), TimeUnits.HOUR)
        in (22 * 60 * 60..26 * 60 * 60-1) -> "день"
        in (26 * 60 * 60..360 * 24 * 60 * 60) ->DeltaTimeToString(deltaTime / (24 * 60 * 60), TimeUnits.DAY)

        else -> "год"
    }
    if (rez.equals("только что")) return rez
    else if(rez.equals("год")&&deltaTime<0) return "более чем через год"
    else if(rez.equals("год")&&deltaTime>0) return "более года назад"
    else if (deltaTime < 0) return "через " + rez
    else return rez + " назад"
}

fun DeltaTimeToString(dTime: Long, unit: TimeUnits): String {

    var l = Math.abs(dTime)
    var time: Array<String> = when (unit) {
        TimeUnits.HOUR -> arrayOf(" час", " часа", " часов")
        TimeUnits.DAY -> arrayOf(" день", " дня", " дней")
        else -> arrayOf(" минута", " минуты", " минут")
    }
    var delta = if (l>20) l % 10 else l
    when (delta) {
        in (5..20) -> return l.toString() + time[2]
        in (2..4) -> return l.toString() + time[1]
        else -> return l.toString() + time[0]
    }
}

