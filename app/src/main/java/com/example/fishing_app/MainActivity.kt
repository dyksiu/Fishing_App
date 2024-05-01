package com.example.fishing_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var buttonHandler: ButtonHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonHandler = ButtonHandler(this)

        findViewById<Button>(R.id.button1).setOnClickListener {
            buttonHandler.startFirstActivity()
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            buttonHandler.startSecondActivity()
        }
        findViewById<Button>(R.id.button3).setOnClickListener {
            buttonHandler.startThirdActivity()
        }

    }
}