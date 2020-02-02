package com.etu.client.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.etu.client.data.model.Diet
import com.etu.client.data.model.Ingredient

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    val dietList: ArrayList<Diet> = ArrayList()
    val ingrList: ArrayList<Ingredient> = ArrayList()
}