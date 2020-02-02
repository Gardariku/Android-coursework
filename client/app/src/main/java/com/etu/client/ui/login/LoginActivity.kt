package com.etu.client.ui.login

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*

import com.etu.client.R
import com.etu.client.baseURL
import com.etu.client.currentUser
import com.etu.client.data.model.User
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private val API_KEY = "Auth"
    private val gson = Gson()
    private var requestQueue: RequestQueue? = null
    private var stringRequest: StringRequest?  = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        requestQueue = Volley.newRequestQueue(this)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)

        val button: Button = findViewById(R.id.login)
        var fullURL = "${baseURL}/user/auth"

        button.setOnClickListener {
            if(username.text != null && password.text != null) {
                val auth: HashMap<String, String> = hashMapOf()
                auth.put("name", username.text.toString())
                auth.put("password", password.text.toString())

                val gsonString: String = gson.toJson(auth)
                val json = JSONObject(gsonString)

                val request = JsonObjectRequest(Request.Method.POST,fullURL,json,
                        Response.Listener { response ->
                            Log.d("Add", response.toString())

                            val jsonResponse = JSONObject(response.toString())
                            var user: User? = gson.fromJson(jsonResponse.toString(), User::class.java)

                            if(user == null) {
                                fullURL = "${baseURL}/user/registrate"

                                val regRequest = JsonObjectRequest(Request.Method.POST,fullURL,json,
                                        Response.Listener { regresponse ->
                                            Log.d("Add", response.toString())

                                            val jsonRegResponse = JSONObject(response.toString())
                                            user = gson.fromJson(jsonRegResponse.toString(), User::class.java)
                                            Toast.makeText(this, "Registration completed", Toast.LENGTH_SHORT).show()
                                            currentUser = user
                                            this@LoginActivity.finish()

                                        }, Response.ErrorListener{

                                    Log.d("Error", it.toString())
                                })
                            } else {
                                currentUser = user
                                this@LoginActivity.finish()
                            }

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


/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }


        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
