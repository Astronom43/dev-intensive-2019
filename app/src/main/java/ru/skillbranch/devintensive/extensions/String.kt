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

fun String.stripHtml():String{
    var rg = Regex("<.*?>")
    var rg1 = Regex("&[a-zA-Z0-9#]*?;")
    var rg2=Regex("\\s+")
    var tmp = this.replace(rg,"").replace(rg1,"").replace(rg2," ")

   // val strB = StringBuilder()

    return tmp
}