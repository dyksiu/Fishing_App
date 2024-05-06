package com.example.fishing_app

import FishListFragment
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.view.View

class ActivityOne : AppCompatActivity() {
    private lateinit var buttonBar1: Button
    private lateinit var buttonBar2: Button
    private lateinit var buttonBar3: Button
    private lateinit var mainView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_one)

        mainView = findViewById(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonBar1 = findViewById(R.id.buttonBar1)
        buttonBar2 = findViewById(R.id.buttonBar2)
        buttonBar3 = findViewById(R.id.buttonBar3)

        buttonBar1.setOnClickListener {
            changeBackgroundToBlack()
            hideButtons()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, FishListFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        buttonBar2.setOnClickListener {
            val intent = Intent(this, Activity_Calendar::class.java)
            startActivity(intent)
        }
        buttonBar3.setOnClickListener {
            val intent = Intent(this, Activity_Hook::class.java)
            startActivity(intent)
        }

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                resetBackgroundToImage()
                showButtons()
            }
        }
    }

    private fun hideButtons() {
        buttonBar1.visibility = View.GONE
        buttonBar2.visibility = View.GONE
        buttonBar3.visibility = View.GONE
    }

    private fun showButtons() {
        buttonBar1.visibility = View.VISIBLE
        buttonBar2.visibility = View.VISIBLE
        buttonBar3.visibility = View.VISIBLE
    }

    private fun changeBackgroundToBlack() {
        mainView.setBackgroundColor(getResources().getColor(android.R.color.black))
    }

    private fun resetBackgroundToImage() {
        mainView.setBackgroundResource(R.drawable.tutorial_layout) // Asumuje, że tło jest ustawione jako drawable
    }
}