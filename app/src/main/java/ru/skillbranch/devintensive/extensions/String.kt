package ru.skillbranch.devintensive.extensions

fun String.truncate(count:Int=16):String{
    val suf = "..."
    val strRez = this.trim().take(count)
    val strRezTr = strRez.trimEnd()
    val strB = StringBuilder(strRezTr)
    if (this.trim().length>strRezTr.length&&
        (strRez.length == strRezTr.length
        || strRez.length-1==strRezTr.length)) strB.append(suf)
    return strB.toString()

}