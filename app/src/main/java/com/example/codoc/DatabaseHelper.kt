package com.example.codoc

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DatabaseHelper(var context: Context): SQLiteOpenHelper(
    context,DATABASE_NAME,null,DATABASE_VERSION){
    companion object {
        private val DATABASE_NAME = "Codoc"
        private val DATABASE_VERSION = 1
        //table name
        private val TABLE_ACCOUNT = "akunpasien"
        //column account table
        private val COLUMN_EMAIL = "email"
        private val COLUMN_NAME = "name"
        private val COLUMN_DATEOFBIRTH = "date_of_birth"
        private val COLUMN_NOHP = "nohp"
        private val COLUMN_PASSWORD = "password"
    }
    //create table account sql query
    private val CREATE_ACCOUNT_TABLE = ("CREATE TABLE " + TABLE_ACCOUNT + "("
            + COLUMN_EMAIL + " TEXT PRIMARY KEY, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_DATEOFBIRTH + " TEXT, "  // Add the new column
            + COLUMN_NOHP + " TEXT, "
            + COLUMN_PASSWORD + " TEXT)")

    //drop table account sql query
    private val DROP_ACCOUNT_TABLE = "DROP TABLE IF EXISTS $TABLE_ACCOUNT"

    override fun onCreate(p0: SQLiteDatabase?){
        p0?.execSQL(CREATE_ACCOUNT_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int){
        p0?.execSQL(DROP_ACCOUNT_TABLE)
        onCreate(p0)
    }

    //login check
    fun checkLogin(email: String, password: String): Boolean {
        println("Email: $email, Password: $password")
        try {
            val columns = arrayOf(COLUMN_NAME)
            val db = this.readableDatabase
            val selection = "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
            val selectionArgs = arrayOf(email.trim(), password.trim())

            val cursor = db.query(
                TABLE_ACCOUNT,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
            )

            val cursorCount = cursor.count
            cursor.close()
            db.close()

            return cursorCount > 0
        } catch (e: Exception) {
            // Log the exception or handle it as needed
            e.printStackTrace()
            return false
        }
    }

    fun addAccount(email: String, name: String, dateOfBirth: String, noHp: String, password: String) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_EMAIL, email)
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_DATEOFBIRTH, dateOfBirth)
        values.put(COLUMN_NOHP, noHp)
        values.put(COLUMN_PASSWORD, password)

        val result = db.insert(TABLE_ACCOUNT, null, values)

        if (result == -1L) {
            Toast.makeText(context, "Register failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Register success, " +
                    "Please login using your new account", Toast.LENGTH_SHORT).show()
        }

        db.close()
    }
    //FIXED REGISTER!!!
    @SuppressLint("Range")
    fun checkData(email:String):String{
        val colums = arrayOf(COLUMN_NAME)
        val db = this.readableDatabase
        val selection = "$COLUMN_EMAIL = ?"
        val selectionArgs = arrayOf(email)
        var name:String = ""

        val cursor = db.query(TABLE_ACCOUNT,
            colums, selection, selectionArgs,null,null,null)

        if(cursor.moveToFirst()){
            name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
        }
        cursor.close()
        db.close()
        return name
    }
}