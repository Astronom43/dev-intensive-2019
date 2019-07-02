package ru.skillbranch.devintensive.models

import java.util.*

abstract class BaseMessage(val id: String,
                           val from: User?,
                           val chat: Chat,
                           val isIncoming: Boolean = false,
                           val date: Date = Date())
{
abstract fun formatMessage():String
    companion object AbstractFactory{
        var lastId = -1
        fun makeMessage(from: User?, chat: Chat, date: Date = Date(), payload:Any, type:String, isIncoming: Boolean = false):BaseMessage{
            lastId++
            return when(type){
                "text" -> TextMessage(id ="$lastId", chat = chat, date = date, from = from, text = payload as String, isIncoming = isIncoming)
                else -> ImageMessage(id ="$lastId", chat = chat, date = date, from = from, image = payload as String, isIncoming = isIncoming)
            }
        }

    }
}
