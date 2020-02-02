package com.etu.client.data.model

import java.sql.Blob


class Ingredient( id: Int = 0, name: String? = null,
                  proteins: Double? = null, fats: Double? = null,
                  carbohydrates: Double? = null, calories: Double? = null,
                  cost: Double? = null, avatar: Blob? = null) {

    var id = 0

    var name: String? = null

    var proteins: Double? = null

    var fats: Double? = null

    var carbohydrates: Double? = null

    var calories: Double? = null

    var cost: Double? = null

    var avatar: Blob? = null

    //var composition: Collection<Compositions>? = null

}