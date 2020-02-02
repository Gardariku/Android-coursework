package com.etu.client.data.model

class Diet(id: Int, name: String?, description: String?, bannedProperties: Set<Property>?) {

    var id = 0

    var name: String? = null

    var description: String? = null

    var bannedProperties: Set<Property>? = null

    //private val appropriateDishes: Set<Dish>? = null

    override fun toString() : String {
        return name!!
    }
}