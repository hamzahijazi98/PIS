package com.uni.pis.Data.EventData

class Message(val text: String, val isBelongsToCurrentUser: Boolean,val SenderName:String) {
    fun getText(): Any
    {
        return text
    }
}