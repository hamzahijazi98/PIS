package com.uni.pis.data

class Message(val text: String, val isBelongsToCurrentUser: Boolean) {
    fun getText(): Any
    {
        return text
    }
}