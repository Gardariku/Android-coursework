package com.etu.client.ui.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.etu.client.R
import com.etu.client.baseURL
import com.etu.client.data.model.Compositions
import com.etu.client.data.model.Dish
import com.etu.client.data.model.Property
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_dish.*
import org.json.JSONObject

class AddDishActivity : AppCompatActivity() {

    private val properties: Set<Property> = hashSetOf()
    private val compositions: List<Compositions> = arrayListOf()
    private val fullURL = "${baseURL}/dish/add"
    private val gson = Gson()
    private var requestQueue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_dish)
        requestQueue = Volley.newRequestQueue(this)

        buttonConfirmAdd.setOnClickListener {
            if(editAddName.text != null && editAddRecipe.text != null && editAddType.text != null) {
                val newDish = Dish(editAddName.text.toString(), editAddRecipe.text.toString(), properties, compositions, editAddType.text.toString())

                val gsonString: String = gson.toJson(newDish)
                val json = JSONObject(gsonString)

                val request = JsonObjectRequest(Request.Method.POST,fullURL,json,
                        Response.Listener { response ->
                            Log.d("Add", response.toString())
                            this.finish()
                        }, Response.ErrorListener{

                    Log.d("Error", it.toString())
                })

                // Volley request policy, only one time request to avoid duplicate transaction
                request.retryPolicy = DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        // 0 means no retry
                        0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
                        1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )

                // Добавиить запрос в очередь
                requestQueue?.add(request)
            }
            }
        }
    }
