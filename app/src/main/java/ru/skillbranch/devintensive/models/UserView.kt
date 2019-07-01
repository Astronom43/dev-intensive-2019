package ru.skillbranch.devintensive.models

class UserView(
    val id: String,
    val fullName: String,
    val nickName: String,
    var avatar: String? = null,
    var status: String? = "offline",
    var initial:String?){

    fun printMe():Unit{
        println("""
            id: ${id}
            fullName: ${fullName}
            nickName: ${nickName}
            avatar: ${avatar}
            status: ${status}
            initial: ${initial}
        """.trimIndent())
    }
}