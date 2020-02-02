package com.etu.client.data.model


class Menu( id: MenuID? = null,
            user: User? = null, dish: Dish? = null,
            day: Int? = null, quantity: Double? = null) {

    var id: MenuID? = null
    var user: User? = null
    var dish: Dish? = null

    var day: Int? = null

    var quantity: Double? = null

}