package com.example.hotelomsai

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.*

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "hotel_logbook.db"
        private const val DATABASE_VERSION = 2

        const val TABLE_GUESTS = "guests"
        const val COL_ID = "id"
        const val COL_FIRSTNAME = "firstName"
        const val COL_LASTNAME = "lastName"
        const val COL_ROOMNO = "roomNo"
        const val COL_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_GUESTS (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_FIRSTNAME TEXT, " +
                "$COL_LASTNAME TEXT, " +
                "$COL_ROOMNO TEXT, " +
                "$COL_DATE TEXT)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_GUESTS")
        onCreate(db)
    }

    // Insert guest with current date
    fun insertGuest(firstName: String, lastName: String, roomNo: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COL_FIRSTNAME, firstName)
            put(COL_LASTNAME, lastName)
            put(COL_ROOMNO, roomNo)
            val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            put(COL_DATE, date)
        }
        val result = db.insert(TABLE_GUESTS, null, contentValues)
        db.close()
        return result != -1L
    }

    // Get all guests
    fun getAllGuests(): List<Guest> {
        val guestList = mutableListOf<Guest>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_GUESTS ORDER BY $COL_ID DESC", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID))
                val firstName = cursor.getString(cursor.getColumnIndexOrThrow(COL_FIRSTNAME))
                val lastName = cursor.getString(cursor.getColumnIndexOrThrow(COL_LASTNAME))
                val roomNo = cursor.getString(cursor.getColumnIndexOrThrow(COL_ROOMNO))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE))

                guestList.add(Guest(id, firstName, lastName, roomNo, date))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return guestList
    }
}
