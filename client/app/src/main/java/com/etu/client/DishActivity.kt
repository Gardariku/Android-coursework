package com.etu.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.etu.client.data.model.Compositions
import com.etu.client.data.model.Property
import kotlinx.android.synthetic.main.activity_dish.*

//Заполнение данных о конкретном блюде
class DishActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish)

        val dish = currentDish

        textViewName.text = currentDish?.name
        textViewRecipe.text = currentDish?.recipe
        imageDish.setImageResource(R.drawable.first)

        var ingredientText: String = ""
        for(ingredient: Compositions in currentDish?.composition!!) {
            ingredientText += "${ingredient.ingredient!!.name}(${ingredient.quantity} гр.), "
        }
        textViewIngredients.text = ingredientText.substring(0, ingredientText.lastIndex - 1) + '.'

        var propText: String = ""
        if(currentDish!!.properties!!.size > 0) {
            for (prop: Property in currentDish?.properties!!) {
                propText += "${prop.name}, "
            }
            textViewProperties.text = propText.substring(0, propText.lastIndex - 1) + '.'
        } else textViewProperties.text = propText
    }
}
