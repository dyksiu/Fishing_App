package com.example.fishing_app

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Activity_Hook : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var descriptionText: TextView
    private var currentIndex = 0

    // Tablice z ID zasobów obrazów i opisów
    private val images = arrayOf(
        R.drawable.hook_1, R.drawable.hook_2, R.drawable.hook_3,
        R.drawable.hook_4, R.drawable.hook_5
    )

    private val descriptions = arrayOf(
        R.string.description_image1, R.string.description_image2,
        R.string.description_image3, R.string.description_image4,
        R.string.description_image5
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hook)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageView = findViewById(R.id.imageView)
        descriptionText = findViewById(R.id.descriptionText)
        updateContent()

        imageView.setOnClickListener {
            currentIndex = (currentIndex + 1) % images.size  // cykliczne przełączanie
            updateContent()
        }



    }
    private fun updateContent() {
        imageView.setImageResource(images[currentIndex])
        descriptionText.setText(descriptions[currentIndex])
    }
}