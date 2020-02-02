package com.etu.client.ui.dashboard


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.etu.client.R
import com.etu.client.baseURL
import com.etu.client.data.model.Diet
import com.etu.client.data.model.Ingredient
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.json.JSONArray

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    lateinit var ingredientAdapter: IngredientAdapter
    lateinit var dietAdapter: DietAdapter

    private val API_KEY = "Ingredients and diets"
    private val gson = Gson()
    private var requestQueue: RequestQueue? = null
    private var stringRequest: StringRequest?  = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(this, Observer {
            textView.text = it
        })

        //if(tabLayout.selectedTabPosition == 1) {

        // Организуем очередь запросов
        requestQueue = Volley.newRequestQueue(this.context)

        //Настройка RecyclerView для списка продуктов
        val anotherRecycler: RecyclerView = root.findViewById(R.id.itemRecycler)

        anotherRecycler.layoutManager = LinearLayoutManager(this.context)

        ingredientAdapter = IngredientAdapter(dashboardViewModel.ingrList)
        dietAdapter = DietAdapter(dashboardViewModel.dietList)

        anotherRecycler.adapter = ingredientAdapter

        loadModelIngredient()
        loadModelDiet()

        val tabLayout: TabLayout = root.findViewById(R.id.tabLayout)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (anotherRecycler.adapter == ingredientAdapter) {
                    anotherRecycler.adapter = dietAdapter
                    anotherRecycler.refreshDrawableState()
                } else {
                    anotherRecycler.adapter = ingredientAdapter
                    anotherRecycler.refreshDrawableState()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        return root
    }

    private fun loadModelIngredient() {
        val totalURL = "$baseURL/ingredient/all"
        Log.d("url", totalURL)

        //Создаем запрос
        stringRequest = object : StringRequest(
                Request.Method.GET, totalURL,
                Response.Listener { response -> //Выполняется при получении ответа
                    val precount = dashboardViewModel.ingrList.count()

                    //Добавление в массив моделей отдельных json объектов типа Dish
                    val quoteJsonArray = JSONArray(response)
                    for (i in 0 until quoteJsonArray.length()) {
                        val singleObject = quoteJsonArray.getJSONObject(i)
                        Log.d("json", singleObject.toString())

                        //Парсинг объекта с помощью Gson
                        val gsonparse = gson.fromJson(singleObject.toString(), Ingredient::class.java)
                        dashboardViewModel.ingrList.add(gsonparse)
                    }

                    //Изменить общее кол-во ячеек при необходимости
                    if(quoteJsonArray.length()>0){
                        ingredientAdapter.notifyItemRangeChanged(precount,dashboardViewModel.ingrList.count())
                    }

                },
                Response.ErrorListener { error ->
                    Log.d("ERROR", "error => " + error.toString())
                }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params = HashMap<String, String>()
                params["X-Mashape-Key"] = API_KEY
                params["Accept"] = "application/json"
                return params
            }
        }

        //Добавление запроса в очередь
        requestQueue?.add(stringRequest)
    }

    private fun loadModelDiet() {
        val totalURL = "$baseURL/diet/all"
        Log.d("url", totalURL)

        //Создаем запрос
        stringRequest = object : StringRequest(
                Request.Method.GET, totalURL,
                Response.Listener { response -> //Выполняется при получении ответа
                    val precount = dashboardViewModel.dietList.count()

                    //Добавление в массив моделей отдельных json объектов типа Dish
                    val quoteJsonArray = JSONArray(response)
                    for (i in 0 until quoteJsonArray.length()) {
                        val singleObject = quoteJsonArray.getJSONObject(i)
                        Log.d("json", singleObject.toString())

                        //Парсинг объекта с помощью Gson
                        val gsonparse = gson.fromJson(singleObject.toString(), Diet::class.java)
                        dashboardViewModel.dietList.add(gsonparse)
                    }

                    //Изменить общее кол-во ячеек при необходимости
                    if(quoteJsonArray.length()>0){
                        dietAdapter.notifyItemRangeChanged(precount,dashboardViewModel.dietList.count())
                    }

                },
                Response.ErrorListener { error ->
                    Log.d("ERROR", "error => " + error.toString())
                }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params = HashMap<String, String>()
                params["X-Mashape-Key"] = API_KEY
                params["Accept"] = "application/json"
                return params
            }
        }

        //Добавление запроса в очередь
        requestQueue?.add(stringRequest)
    }
}