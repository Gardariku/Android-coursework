package com.etu.client.data.model

import java.io.Serializable
import java.util.*

class MenuID(userID: Int, dishID: Int) : Serializable {

    private val userID = 0
    private val dishID = 0

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as MenuID
        return userID == that.userID &&
                dishID == that.dishID
    }

    override fun hashCode(): Int {
        return Objects.hash(userID, dishID)
    }
}