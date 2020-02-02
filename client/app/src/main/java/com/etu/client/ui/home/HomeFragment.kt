package com.etu.client.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
import com.etu.client.DishActivity
import com.etu.client.R
import com.etu.client.baseURL
import com.etu.client.currentDish
import com.etu.client.data.model.Diet
import com.etu.client.data.model.Dish
import com.etu.client.data.model.Type
import org.json.JSONArray
import com.google.gson.Gson
import java.nio.charset.Charset

//Лень переписывать стандартные названия фрагментов, этот отвечает за просмотр находящихся в базе блюд.
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    lateinit var dishAdapter: DishAdapter

    private val API_KEY = "Dishes"
    private val gson = Gson()
    private var requestQueue: RequestQueue? = null
    private var stringRequest: StringRequest?  = null

    private var typeFilter: String? = null
    private var dietFilter: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("test","onCreateView homeFragment")
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })

        val addButton: Button = root.findViewById(R.id.addButton)
        addButton.setOnClickListener {
            val addDishIntent = Intent(this.context, AddDishActivity::class.java)
            startActivity(addDishIntent)
        }

        // Организуем очередь запросов
        requestQueue = Volley.newRequestQueue(this.context)

        //Настройка RecyclerView для списка блюд
        val dishRecycler: RecyclerView = root.findViewById(R.id.dishRecycler)
        dishRecycler.layoutManager = LinearLayoutManager(this.context)
        dishAdapter = DishAdapter(homeViewModel.dishList)
        dishAdapter.onItemClick = { dish ->
            Log.d("Dish", dish.name!!)
            //Вызов окна информации о блюде при нажатии на ячейку
            val dishIntent = Intent(this.context, DishActivity::class.java)
            currentDish = dish
            startActivity(dishIntent)
        }
        dishRecycler.adapter = dishAdapter

        //Настройка спиннеров
        val typeSpinner: Spinner = root.findViewById(R.id.spinner_type)
        val dietSpinner: Spinner = root.findViewById(R.id.spinner_diet)
        val typeAdapter: ArrayAdapter<String> = ArrayAdapter(this.context!!, R.layout.spinner_item, homeViewModel.typeList)
        val dietAdapter: ArrayAdapter<Diet> = ArrayAdapter(this.context!!, R.layout.spinner_item2, homeViewModel.dietList)
        typeSpinner.adapter = typeAdapter
        dietSpinner.adapter = dietAdapter
        typeSpinner.prompt = "Select preferred type of dish"
        dietSpinner.prompt = "Select your diet"
        typeSpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                typeFilter = typeSpinner.selectedItemPosition.toString()
                Toast.makeText(activity,
                        typeSpinner.selectedItemPosition.toString(), Toast.LENGTH_SHORT).show()
                (view as TextView).text = typeSpinner.selectedItemPosition.toString()
                view.setTextColor(Color.BLACK)
                textView.text = typeSpinner.selectedItemPosition.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(activity,
                        "Nothing", Toast.LENGTH_SHORT).show()
            }
        }
        dietSpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                dietFilter = dietSpinner.selectedItemPosition.toString()
                Toast.makeText(activity,
                        dietSpinner.selectedItemPosition.toString(), Toast.LENGTH_SHORT).show()
                (view as TextView).text = dietSpinner.selectedItemPosition.toString()
                view.setTextColor(Color.BLACK)

                textView.text = dietSpinner.selectedItemPosition.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(activity,
                        "Nothing", Toast.LENGTH_SHORT).show()
            }
        }



        loadModelType()
        loadModelDiet()
        loadModelDish()

        return root
    }

    //Загрузка моделей ячеек класса Dish
    //Ради простоты загружаются все разом
    private fun loadModelDish() {
        val totalURL = "${baseURL}/dish/all"
        Log.d("url", totalURL)

        //Создаем запрос
        stringRequest = object : StringRequest(
            Request.Method.GET, totalURL,
            Response.Listener { response -> //Выполняется при получении ответа
                val precount = homeViewModel.dishList.count()

                //Добавление в массив моделей отдельных json объектов типа Dish
                val quoteJsonArray = JSONArray(response)
                for (i in 0 until quoteJsonArray.length()) {
                    val singleObject = quoteJsonArray.getJSONObject(i)
                    Log.d("json", singleObject.toString())

                    //Парсинг объекта с помощью Gson
                    val gsonparse = gson.fromJson(singleObject.toString(), Dish::class.java)
                    homeViewModel.dishList.add(gsonparse)
                }

                //Изменить общее кол-во ячеек при необходимости
                if(quoteJsonArray.length()>0){
                    dishAdapter.notifyItemRangeChanged(precount,homeViewModel.dishList.count())
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
        val totalURL = "${baseURL}/diet/all"
        Log.d("url", totalURL)

        //Создаем запрос
        stringRequest = object : StringRequest(
                Request.Method.GET, totalURL,
                Response.Listener { response -> //Выполняется при получении ответа
                    //Добавление в массив моделей отдельных json объектов типа Dish
                    val quoteJsonArray = JSONArray(response)
                    for (i in 0 until quoteJsonArray.length()) {
                        val singleObject = quoteJsonArray.getJSONObject(i)
                        Log.d("json", singleObject.toString())

                        //Парсинг объекта с помощью Gson
                        val gsonparse = gson.fromJson(singleObject.toString(), Diet::class.java)
                        homeViewModel.dietList.add(gsonparse)
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

    //Ну тут халтура
    private fun loadModelType() {
        for(type in Type.values())
            homeViewModel.typeList.add(type.engname)
    }
}