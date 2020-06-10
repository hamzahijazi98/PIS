package com.uni.pis.Data.EventData

import org.junit.Test

import org.junit.Assert.*

class MessageTest {

    @Test
    fun getText() {
        var text="hello test function"
        var altext="althello test function"
        var thisuser=true
        var otheruser =false
        var wrongname= "ali mohammed"
        var username="hamza hijazi"
        var message=Message(text,thisuser,username)
        assertEquals(text,message.text)
        assertEquals(username,message.SenderName)
        assertEquals(thisuser,message.isBelongsToCurrentUser)
        assertNotEquals(altext,message.text)
        assertNotEquals(wrongname,message.SenderName)
        assertNotEquals(otheruser,message.isBelongsToCurrentUser)
    }
}