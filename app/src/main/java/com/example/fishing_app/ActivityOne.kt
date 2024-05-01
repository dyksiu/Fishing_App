package com.example.fishing_app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.content.Intent
class ActivityOne : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_one)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonBar1: Button = findViewById(R.id.buttonBar1)
        val buttonBar2: Button = findViewById(R.id.buttonBar2)
        val buttonBar3: Button = findViewById(R.id.buttonBar3)

        buttonBar1.setOnClickListener {
            val intent = Intent(this, Acitivity_Fish::class.java)
            startActivity(intent)
        }
        buttonBar2.setOnClickListener {
            val intent = Intent(this, Activity_Calendar::class.java)
            startActivity(intent)
        }

        buttonBar3.setOnClickListener {
            val intent = Intent(this, Activity_Hook::class.java)
            startActivity(intent)
        }

    }
}