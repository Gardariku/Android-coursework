package com.etu.client.data.model

import java.sql.Blob

data class Dish(var id: Int? = null, var name: String? = null, var recipe: String? = null,
                var type: String? = null, var avatar: Blob? = null, var cost: Double? = null,
                var proteins: Double? = null, var fats: Double? = null,
                var carbohydrates: Double? = null, var calories: Double? = null,
                var properties: Set<Property>? = null,
                var appropriateDiets: Set<Diet>? = null,
                var composition: List<Compositions>? = null) {

    constructor(name: String?, recipe: String?, properties: Set<Property>?, composition: List<Compositions>?, type: String?)
    : this(null, name, recipe, type, null, null, null, null, null, null, properties, null, composition)

    //var ingestions: Collection<Menu>? = null

}

/*
package com.etu.client.data.model

import java.sql.Blob

data class Dish(var id: Int = 0, var name: String? = null, recipe: String? = null,
           avatar: Blob? = null, cost: Double? = null,
           proteins: Double? = null, fats: Double? = null,
           carbohydrates: Double? = null, calories: Double? = null,
           properties: Set<Property>? = null,
           appropriateDiets: Set<Diet>? = null,
           composition: Collection<Compositions>? = null) {


    var recipe: String? = null

    var avatar: Blob? = null

    var cost: Double? = null

    var proteins: Double? = null

    var fats: Double? = null

    var carbohydrates: Double? = null

    var calories: Double? = null

    var properties: Set<Property>? = null

    private val appropriateDiets: Set<Diet>? = null

    var composition: Collection<Compositions>? = null

    //var ingestions: Collection<Menu>? = null

}
 */