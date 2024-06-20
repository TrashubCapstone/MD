package com.android.trashub

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class HomepageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val btnGeser: MaterialButton = findViewById(R.id.btnGeser)

        // Retrieve the user name passed from InputActivity
        val userName = intent.getStringExtra("user_name")

        btnGeser.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("user_name", userName)
            }
            startActivity(intent)
        }
    }
}