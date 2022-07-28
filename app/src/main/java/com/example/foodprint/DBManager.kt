package com.example.foodprint

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBManager(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE foodlistDB (name text, bigname text, prot Integer, fatce Integer, chocdf Integer," +
                "sugar Integer, ca Integer, fe Integer, p Integer, k Integer, na Integer," +
                "vita Integer, vitc Integer, vitd Integer, chole Integer, fasat Integer, fatrn Integer, servsize Integer);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}