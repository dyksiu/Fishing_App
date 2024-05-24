package com.example.fishing_app.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CatchDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // sa tu:
    // -> stale do definicji nazwy bazy danych, wersji, oraz nazwy tabel i kolumn
    // czemu wersja 2? -> bo w trakcie robienia rozszerzylem baze danych o mozliwosc
    //                    zapisywania w niej sciezki do zdjec -> nowa kolumna image_path
    companion object {
        private const val DATABASE_NAME = "catches.db"
        private const val DATABASE_VERSION = 2
        const val TABLE_NAME = "catches"
        const val COLUMN_ID = "id"
        const val COLUMN_FISH_TYPE = "fish_type"
        const val COLUMN_WEIGHT = "weight"
        const val COLUMN_IMAGE_PATH = "image_path"
    }

    // wywoluje sie podczas tworzenia bazy danych -> tworzy sie tabela "catches" w ktorej
    //                                               sa kolumny zdefiniowane wyzej
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_FISH_TYPE TEXT, "
                + "$COLUMN_WEIGHT REAL, "
                + "$COLUMN_IMAGE_PATH TEXT)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db?.execSQL("ALTER TABLE $TABLE_NAME ADD COLUMN $COLUMN_IMAGE_PATH TEXT")
        }
    }
}
