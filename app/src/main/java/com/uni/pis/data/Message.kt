package com.uni.pis.data

class Message(val text: String, val isBelongsToCurrentUser: Boolean,val SenderName:String) {
    fun getText(): Any
    {
        return text
    }
}