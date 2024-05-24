package com.example.fishing_app

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fishing_app.database.CatchDatabaseHelper
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FishingCatchActivity : AppCompatActivity() {

    // tu sa zmienne dla:
    // -> UI
    // -> bazy danych
    // -> sciezki obrazu
    // -> REQUEST_IMAGE_CAPTURE odpowiada za identyfikacje zadania przechwytywania obrazu
    private lateinit var dbHelper: CatchDatabaseHelper
    private lateinit var fishTypeEditText: EditText
    private lateinit var weightEditText: EditText
    private lateinit var imageView: ImageView
    private lateinit var saveButton: Button
    private lateinit var captureButton: Button
    private var imagePath: String? = null
    private val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fishing_catch)

        dbHelper = CatchDatabaseHelper(this)

        fishTypeEditText = findViewById(R.id.fishTypeEditText)
        weightEditText = findViewById(R.id.weightEditText)
        imageView = findViewById(R.id.imageView)
        saveButton = findViewById(R.id.saveButton)
        captureButton = findViewById(R.id.captureButton)

        captureButton.setOnClickListener {
            dispatchTakePictureIntent()
        }

        saveButton.setOnClickListener {
            val fishType = fishTypeEditText.text.toString()
            val weight = weightEditText.text.toString().toDoubleOrNull()

            if (fishType.isNotEmpty() && weight != null) {
                saveCatch(fishType, weight, imagePath)
                Toast.makeText(this, R.string.succesful_save, Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, R.string.check_your_data, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // przechowuje obraz za pomoca aparatu -> startActivityForResult - aktywnosc z oczekiwaniem na zdjecie
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    // wywoluje sie po zakonczeniu dispatchTakePictureIntent -> gdy zdjecie udalo sie zrobić
    // pobieramy bitmape i ustawiamy ja w ImageView zapisujac wewnetrz urzadzenia
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
            saveImageToInternalStorage(imageBitmap)
        }
    }

    // zapisanie pobranej bitmapy obrazu w wewnetrznej pamieci urzadzenia jako JPEG
    // sciezka pliku przechowowywana jest w imagePath i metoda ja zwraca
    private fun saveImageToInternalStorage(bitmap: Bitmap): String {
        val filename = "fish_${System.currentTimeMillis()}.jpg"
        val file = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), filename)
        try {
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        imagePath = file.absolutePath
        return file.absolutePath
    }

    // funkcja ktora zapisuje dane polowu:
    // -> typ ryby
    // -> waga
    // -> sciezka obrazu
    // wszystko zapisuje sie w bazie danych za pomoca CatchDatabaseHelper
    private fun saveCatch(fishType: String, weight: Double, imagePath: String?) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(CatchDatabaseHelper.COLUMN_FISH_TYPE, fishType)
            put(CatchDatabaseHelper.COLUMN_WEIGHT, weight)
            put(CatchDatabaseHelper.COLUMN_IMAGE_PATH, imagePath)
        }
        db.insert(CatchDatabaseHelper.TABLE_NAME, null, values)
    }
}
