package com.etu.client.data.model

import java.util.*

class User(id: Int, name: String? = null,
           sex: Boolean? = false, weight: Int? = null, height: Int? = null,
           age: Int? = null, activity: Double? = null,
           diet: Diet? = null, ingestions: List<Menu>? = null) {

    var id = 0
    var name: String? = null
    var sex: Boolean? = false
    var weight: Int? = null
    var height: Int? = null
    var age: Int? = null
    var activity: Double? = null

    var diet: Diet? = null
    var ingestions: List<Menu>? = null

    var totalCost: Double? = 0.0
    fun calculateCost(): Double {
        var cost = 0.0
        for (menu in ingestions!!) {
            cost += menu.quantity!! / 100 * menu.dish!!.cost!!
        }
        totalCost = cost
        return cost
    }

    var totalProteins = 0.0
    fun calculateProteins(): Double {
        var proteins = 0.0
        for (menu in ingestions!!) {
            proteins += menu.quantity!! / 100 * menu.dish!!.proteins!!
        }
        totalProteins = proteins
        return proteins
    }

    var totalFats = 0.0
    fun calculateFats(): Double {
        var fats = 0.0
        for (menu in ingestions!!) {
            fats += menu.quantity!! / 100 * menu.dish!!.fats!!
        }
        totalFats = fats
        return fats
    }

    var totalCarbohydrates = 0.0
    fun calculateCarbohydrates(): Double {
        var carbohydrates = 0.0
        for (menu in ingestions!!) {
            carbohydrates += menu.quantity!! / 100 * menu.dish!!.carbohydrates!!
        }
        totalFats = carbohydrates
        return carbohydrates
    }

    var totalCalories = 0.0
    fun calculateCalories(): Double {
        var calories = 0.0
        for (menu in ingestions!!) {
            calories += menu.quantity!! / 100 * menu.dish!!.calories!!
        }
        totalCalories = calories
        return calories
    }

    var recommendedProteins = 0.0
    fun calculateRecommendedProteins(): Double {
        recommendedCalories = recommendedCalories / (6 * 4)
        return recommendedCalories
    }

    fun proteinsAreSatisfied(): Boolean {
        return totalProteins >= recommendedProteins
    }

    var recommendedFats = 0.0
    fun calculateRecommendedFats(): Double {
        recommendedFats = recommendedCalories / (6 * 9)
        return recommendedFats
    }

    fun fatsAreSatisfied(): Boolean {
        return totalFats >= recommendedFats
    }

    var recommendedCarbohydrates = 0.0
    fun calculateRecommendedCarbohydrates(): Double {
        recommendedCarbohydrates = recommendedCalories / 6
        return recommendedCarbohydrates
    }

    fun carbohydratesAreSatisfied(): Boolean {
        return totalCarbohydrates >= recommendedCarbohydrates
    }

    var recommendedCalories = 0.0
    fun calculateRecommendedCalories(): Double { //Расчет по формуле Миффлина-Сан Жеора
//для мужчин: (10 x вес (кг) + 6.25 x рост (см) – 5 x возраст (г) + 5) x A;
//для женщин: (10 x вес (кг) + 6.25 x рост (см) – 5 x возраст (г) – 161) x A.
        val calories: Double
        calories = if (sex!!) {
            (10 * weight!! + 6.25 * height!! - 5 * age!! + 5) * activity!!
        } else {
            (10 * weight!! + 6.25 * height!! - 5 * age!! - 161) * activity!!
        }
        recommendedCalories = calories
        return calories
    }

    fun caloriesAreSatisfied(): Boolean {
        return totalCalories >= recommendedCalories
    }

}