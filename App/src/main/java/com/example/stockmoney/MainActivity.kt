package com.example.stockmoney

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun openSignUp(view: View) {
        val intent = Intent(this, SignUp::class.java)
        startActivity(intent)
    }

    fun openLogin(view: View) {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }

    fun startGuest(view: View) {
        val intent = Intent(this, Guest::class.java)
        startActivity(intent)
    }

}