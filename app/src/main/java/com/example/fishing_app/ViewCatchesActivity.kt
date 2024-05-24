package com.example.fishing_app

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fishing_app.database.CatchDatabaseHelper

class ViewCatchesActivity : AppCompatActivity() {

    private lateinit var dbHelper: CatchDatabaseHelper
    private lateinit var scrollView: ScrollView
    private lateinit var catchesContainer: LinearLayout
    private lateinit var deleteIdEditText: EditText
    private lateinit var deleteCatchButton: Button
    private lateinit var deleteAllButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_catches)

        dbHelper = CatchDatabaseHelper(this)
        scrollView = findViewById(R.id.scrollView)
        catchesContainer = findViewById(R.id.catchesContainer)
        deleteIdEditText = findViewById(R.id.deleteIdEditText)
        deleteCatchButton = findViewById(R.id.deleteCatchButton)
        deleteAllButton = findViewById(R.id.deleteAllButton)

        deleteCatchButton.setOnClickListener {
            val idText = deleteIdEditText.text.toString()
            val id = idText.toIntOrNull()
            if (id != null) {
                deleteCatch(id)
            } else {
                Toast.makeText(this, R.string.correct_ID, Toast.LENGTH_SHORT).show()
            }
        }

        deleteAllButton.setOnClickListener {
            deleteAllCatches()
        }

        displayCatches()
    }


    // funkcja odpowiedzialna za robienie zdjec
    private fun displayCatches() {
        catchesContainer.removeAllViews()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            CatchDatabaseHelper.TABLE_NAME,
            arrayOf(CatchDatabaseHelper.COLUMN_ID, CatchDatabaseHelper.COLUMN_FISH_TYPE, CatchDatabaseHelper.COLUMN_WEIGHT, CatchDatabaseHelper.COLUMN_IMAGE_PATH),
            null, null, null, null, null
        )

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(CatchDatabaseHelper.COLUMN_ID))
            val fishType = cursor.getString(cursor.getColumnIndexOrThrow(CatchDatabaseHelper.COLUMN_FISH_TYPE))
            val weight = cursor.getDouble(cursor.getColumnIndexOrThrow(CatchDatabaseHelper.COLUMN_WEIGHT))
            val imagePath = cursor.getString(cursor.getColumnIndexOrThrow(CatchDatabaseHelper.COLUMN_IMAGE_PATH))

            val itemView = layoutInflater.inflate(R.layout.item_catch, catchesContainer, false)
            val textView = itemView.findViewById<TextView>(R.id.catchTextView)
            val imageView = itemView.findViewById<ImageView>(R.id.catchImageView)

            textView.text = "ID: $id, Ryba: $fishType, Waga: $weight kg"
            if (imagePath != null) {
                val bitmap = BitmapFactory.decodeFile(imagePath)
                imageView.setImageBitmap(bitmap)
            }
            catchesContainer.addView(itemView)
        }
        cursor.close()
    }

    private fun deleteAllCatches() {
        val db = dbHelper.writableDatabase
        db.delete(CatchDatabaseHelper.TABLE_NAME, null, null)
        Toast.makeText(this, R.string.all_fishing_deleted, Toast.LENGTH_SHORT).show()
        displayCatches()
    }

    private fun deleteCatch(id: Int) {
        val db = dbHelper.writableDatabase
        db.delete(CatchDatabaseHelper.TABLE_NAME, "${CatchDatabaseHelper.COLUMN_ID} = ?", arrayOf(id.toString()))
        Toast.makeText(this, "Usunięto połów o ID: $id", Toast.LENGTH_SHORT).show()
        displayCatches()
    }
}
