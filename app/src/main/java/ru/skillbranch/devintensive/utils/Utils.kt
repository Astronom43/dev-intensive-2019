package ru.skillbranch.devintensive.utils

//import java.lang.StringBuilder
import java.util.*
import kotlin.text.StringBuilder

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        var fn: List<String>? = fullName?.split(" ")?.filter { a -> a.trim().length > 0 }
        return Pair(fn?.getOrNull(0), fn?.getOrNull(1))
    }

    fun transliteration(payLoad: String, davider: String = " "): String {
        var mapChar: Map<String, String> = mapOf(
            "а" to "a",
            "б" to "b",
            "в" to "v",
            "г" to "g",
            "д" to "d",
            "е" to "e",
            "ё" to "e",
            "ж" to "zh",
            "з" to "z",
            "и" to "i",
            "й" to "i",
            "к" to "k",
            "л" to "l",
            "м" to "m",
            "н" to "n",
            "о" to "o",
            "п" to "p",
            "р" to "r",
            "с" to "s",
            "т" to "t",
            "у" to "u",
            "ф" to "f",
            "х" to "h",
            "ц" to "c",
            "ч" to "ch",
            "ш" to "sh",
            "щ" to "sh'",
            "ъ" to "",
            "ы" to "i",
            "ь" to "",
            "э" to "e",
            "ю" to "yu",
            "я" to "ya"
        )
        var txt = payLoad.split(" ")
        var rez: StringBuilder = StringBuilder()
        txt.forEach { a ->
            a.forEach {
                val isUpp = it.isUpperCase()
                var r: String? = mapChar[it.toLowerCase().toString()]
                if (r == null) r = it.toString()
                if (isUpp) r = r.capitalize()
                rez.append(r)
            }.also { rez.append(davider) }
        }
        return rez.toString().removeSuffix(davider)
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val rez = StringBuilder()
        var v1 = firstName?.trim()?.toUpperCase()
        var v2 = lastName?.trim()?.toUpperCase()
        if (v1 != null && v1.length > 0) rez.append(v1[0])
        if (v2 != null && v2.length > 0) rez.append(v2[0])
        if (rez.toString().equals("")) return null
        else return rez.toString()

    }
}