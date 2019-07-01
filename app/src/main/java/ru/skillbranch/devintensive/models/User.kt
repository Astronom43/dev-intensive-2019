package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User(
    val id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String?,
    var rating: Int = 0,
    var respect: Int = 0,
    var lastVisit: Date? = Date(),
    var isOnline: Boolean = false
) {
    constructor(id: String, firstName: String?, lastName: String?) : this(
        id = id,
        firstName = firstName,
        lastName = lastName,
        avatar = null
    )

    constructor(id: String) : this(id, "John", "Smidt")

    companion object Factory {
        private var id: Int = -1
        fun MakeUser(fulName: String): User {
            id++
            var(fn ,ln) = Utils.purseFullName(fulName)
            return User(id.toString(), fn, ln)
        }

    }
    class Builder(){
        var firstName: String? = null
        var lastName: String? = null
        var id: String = ""
        var avatar: String? = null
        var rating: Int = 0
        var respect: Int = 0
        var lastVisit: Date? = Date()
        var isOnline: Boolean = false

        fun id(i:String) = apply { this.id = i }
        fun firstName(i:String?) = apply { this.firstName = i }
        fun lastName(i:String?) = apply { this.lastName = i }
        fun avatar(i:String?) = apply { this.avatar = i }
        fun rating(i:Int) = apply { this.rating = i }
        fun respect(i:Int) = apply { this.respect = i }
        fun lastVisit(i:Date?) = apply { this.lastVisit = i }
        fun isOnline(i:Boolean) = apply { this.isOnline = i }
        fun build():User{
            return User(id, firstName, lastName,avatar,rating,respect,lastVisit,isOnline)
        }

    }
}
