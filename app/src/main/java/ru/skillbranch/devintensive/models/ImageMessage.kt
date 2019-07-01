package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.extensions.humanizeDif
import java.util.*

class ImageMessage(id: String,
                   from: User?,
                   chat: Chat,
                   isIncoming: Boolean = false,
                   date: Date = Date(),
                   var image: String?

) : BaseMessage( id, from, chat, isIncoming, date) {
    override fun formatMessage(): String {
        return "ID:$id ${from?.firstName} ${from?.lastName} ${if (isIncoming) "получил" else "отправил"} изображение "+
                "$image ${date.humanizeDif()}"
    }
}