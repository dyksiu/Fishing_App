// Klasa odpowiedzialna za obsługę 3 przycisków na glownym ekranie
package com.example.fishing_app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
class ButtonHandler(val context: Context) {
    fun startFirstActivity() {
        val intent = Intent(context, ActivityOne::class.java)
        context.startActivity(intent)
    }

}