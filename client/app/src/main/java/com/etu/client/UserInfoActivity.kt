package com.etu.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_user_info.*
import com.etu.client.R
import com.google.gson.Gson
import org.json.JSONObject

class UserInfoActivity : AppCompatActivity() {

    private val fullURL = "${baseURL}/user/update"
    private val gson = Gson()
    private var requestQueue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        requestQueue = Volley.newRequestQueue(this)

        editTextName.setText(currentUser?.name.toString())
        editTextAge.setText(currentUser?.age?.toString())
        editTextHeight.setText(currentUser?.height?.toString())
        editTextWeight.setText(currentUser?.weight?.toString())
        imageDish.setImageResource(R.drawable.ava)

        buttonSaveUser.setOnClickListener {
            currentUser!!.name = editTextName.text.toString()
            currentUser!!.age = editTextAge.text.toString().toInt()
            currentUser!!.height = editTextHeight.text.toString().toInt()
            currentUser!!.weight = editTextWeight.text.toString().toInt()

            val gsonString: String = gson.toJson(currentUser)
            val json = JSONObject(gsonString)

            val request = JsonObjectRequest(Request.Method.POST,fullURL,json,
                    Response.Listener { response ->
                        Log.d("Add", response.toString())
                        this.finish()
                    }, Response.ErrorListener{

                Log.d("Error", it.toString())
            })

            // Добавиить запрос в очередь
            requestQueue?.add(request)
        }
        }

    }


