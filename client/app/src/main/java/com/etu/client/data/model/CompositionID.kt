package com.etu.client.data.model

import java.io.Serializable
import java.util.*

class CompositionID(dishID: Int, ingredientID: Int) : Serializable {

    private val dishID = 0

    private val ingredientID = 0

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as CompositionID
        return dishID == that.dishID &&
                ingredientID == that.ingredientID
    }

    override fun hashCode(): Int {
        return Objects.hash(dishID, ingredientID)
    }
}