package com.etu.client.data.model

import java.util.*

class Compositions( id: CompositionID? = null,
                    dishes: Dish? = null, ingredient: Ingredient? = null,
                    quantity: Int = 0) {

    var id: CompositionID? = null

    var dishes: Dish? = null

    var ingredient: Ingredient? = null

    var quantity = 0

}