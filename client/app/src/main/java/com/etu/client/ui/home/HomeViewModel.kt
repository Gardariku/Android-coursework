package com.etu.client.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.etu.client.data.model.Diet
import com.etu.client.data.model.Dish
import com.etu.client.data.model.Type

//Мне кажется можно и без этого обойтись, но ладно
class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text

    val typeList: ArrayList<String> = ArrayList()
    val dietList: ArrayList<Diet> = ArrayList()
    val dishList: ArrayList<Dish> = ArrayList()

}