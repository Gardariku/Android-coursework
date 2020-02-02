package com.etu.client

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.etu.client.data.model.Dish
import com.etu.client.data.model.User
import com.etu.client.ui.login.LoginActivity

//Я конектил через usb и утилиту в гугл хроме, очень удобно, для виртуалки заменить коммент снизу
val baseURL = "http://localhost:8080"
var currentDish: Dish? = null
var currentUser: User? = null
//val baseURL = "http://192.168.0.1:8080"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Вызов окна авторизации перед основным активити, неуверен, насколько корректно так делать
        val authIntent = Intent(this, LoginActivity::class.java)
        startActivity(authIntent)

        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    // Панелька сверху, там ничего особо нет
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_dishes, menu)
/*
        menu?.getItem(2)?.setOnMenuItemClickListener {
            val userIntent = Intent(this, UserInfoActivity::class.java)
            startActivity(userIntent)
            true
        }
        */
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_user -> {
                val intent = Intent(this, UserInfoActivity::class.java)
                this.startActivity(intent)
                return true
            }

            else ->
                return false
        }
    }
}
