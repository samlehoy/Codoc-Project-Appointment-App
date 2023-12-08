package com.example.codoc

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.codoc.model.DokterModel
import com.example.codoc.model.ProfileDokterModel
import com.example.codoc.model.ProfilePasienModel


class DatabaseHelper(var context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {
    companion object {
        const val DATABASE_NAME = "Codoc"
        const val DATABASE_VERSION = 2 // Incremented the version number

        // Table names
        const val TABLE_AKUNPASIEN = "akunpasien"
        const val TABLE_AKUNDOKTER = "akundokter"

        // Columns for account pasien
        const val COLUMN_EMAIL_PASIEN = "email"
        const val COLUMN_NAME_PASIEN = "name"
        const val COLUMN_DATEOFBIRTH_PASIEN = "date_of_birth"
        const val COLUMN_NOHP_PASIEN = "nohp"
        const val COLUMN_PASSWORD_PASIEN = "password"

        // Columns for account dokter
        const val COLUMN_EMAIL_DOKTER = "email_dokter"
        const val COLUMN_NAME_DOKTER = "name_dokter"
        const val COLUMN_SPECIALIS_DOKTER = "specialis_dokter"
        const val COLUMN_JADWAL_DOKTER = "jadwal_dokter"
        const val COLUMN_RUMAHSAKIT = "rumah_sakit"
        const val COLUMN_PASSWORD_DOKTER = "password_dokter"
    }

    // Create table account sql query
    private val CREATE_ACCOUNT_TABLE = ("CREATE TABLE IF NOT EXISTS " + TABLE_AKUNPASIEN + "("
            + COLUMN_EMAIL_PASIEN + " TEXT PRIMARY KEY, "
            + COLUMN_NAME_PASIEN + " TEXT, "
            + COLUMN_DATEOFBIRTH_PASIEN + " TEXT, "
            + COLUMN_NOHP_PASIEN + " TEXT, "
            + COLUMN_PASSWORD_PASIEN + " TEXT)")

    private val CREATE_ACCOUNT_TABLE_DOKTER = ("CREATE TABLE IF NOT EXISTS " + TABLE_AKUNDOKTER + "("
            + COLUMN_EMAIL_DOKTER + " TEXT PRIMARY KEY, "
            + COLUMN_NAME_DOKTER + " TEXT, "
            + COLUMN_SPECIALIS_DOKTER + " TEXT, "
            + COLUMN_JADWAL_DOKTER + " TEXT, "
            + COLUMN_RUMAHSAKIT + " TEXT, "
            + COLUMN_PASSWORD_DOKTER + " TEXT)")

    // Drop table account sql query
    private val DROP_ACCOUNT_TABLE = "DROP TABLE IF EXISTS $TABLE_AKUNPASIEN"
    private val DROP_ACCOUNT_TABLE_DOKTER = "DROP TABLE IF EXISTS $TABLE_AKUNDOKTER"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_ACCOUNT_TABLE)
        db.execSQL(CREATE_ACCOUNT_TABLE_DOKTER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_ACCOUNT_TABLE)
        db.execSQL(DROP_ACCOUNT_TABLE_DOKTER)
        onCreate(db)
    }

    //login check
    fun checkLoginPasien(email: String, password: String): Boolean {
        println("Email: $email, Password: $password")
        try {
            val columns = arrayOf(COLUMN_NAME_PASIEN)
            val db = this.readableDatabase
            val selection = "$COLUMN_EMAIL_PASIEN = ? AND $COLUMN_PASSWORD_PASIEN = ?"
            val selectionArgs = arrayOf(email.trim(), password.trim())

            val cursor = db.query(
                TABLE_AKUNPASIEN,
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
    fun checkLoginDokter(email: String, password: String): Boolean {
        println("Email: $email, Password: $password")
        try {
            val columns = arrayOf(COLUMN_NAME_DOKTER)
            val db = this.readableDatabase
            val selection = "$COLUMN_EMAIL_DOKTER = ? AND $COLUMN_PASSWORD_DOKTER = ?"
            val selectionArgs = arrayOf(email.trim(), password.trim())

            val cursor = db.query(
                TABLE_AKUNDOKTER,
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

    fun addAccountPasien(email: String, name: String, dateOfBirth: String, noHp: String, password: String) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_EMAIL_PASIEN, email)
        values.put(COLUMN_NAME_PASIEN, name)
        values.put(COLUMN_DATEOFBIRTH_PASIEN, dateOfBirth)
        values.put(COLUMN_NOHP_PASIEN, noHp)
        values.put(COLUMN_PASSWORD_PASIEN, password)

        val result = db.insert(TABLE_AKUNPASIEN, null, values)

        if (result == -1L) {
            Toast.makeText(context, "Register failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Register success, " +
                    "Please login using your new account", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    fun addAccountDokter(email: String, name: String, specialis: String, password: String) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_EMAIL_DOKTER, email)
        values.put(COLUMN_NAME_DOKTER, name)
        values.put(COLUMN_SPECIALIS_DOKTER, specialis)
        values.put(COLUMN_PASSWORD_DOKTER, password)

        val result = db.insert(TABLE_AKUNDOKTER, null, values)

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
    fun checkDataPasien(email:String):String{
        val colums = arrayOf(COLUMN_NAME_PASIEN)
        val db = this.readableDatabase
        val selection = "$COLUMN_EMAIL_PASIEN = ?"
        val selectionArgs = arrayOf(email)
        var name:String = ""

        val cursor = db.query(TABLE_AKUNPASIEN,
            colums, selection, selectionArgs,null,null,null)

        if(cursor.moveToFirst()){
            name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PASIEN))
        }
        cursor.close()
        db.close()
        return name
    }
    @SuppressLint("Range")
    fun checkDataDokter(email:String):String{
        val colums = arrayOf(COLUMN_NAME_DOKTER)
        val db = this.readableDatabase
        val selection = "$COLUMN_EMAIL_DOKTER = ?"
        val selectionArgs = arrayOf(email)
        var name:String = ""

        val cursor = db.query(TABLE_AKUNDOKTER,
            colums, selection, selectionArgs,null,null,null)

        if(cursor.moveToFirst()){
            name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DOKTER))
        }
        cursor.close()
        db.close()
        return name
    }

    fun editProfilePasien(menu:ProfilePasienModel){
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COLUMN_EMAIL_PASIEN, menu.email)
        values.put(COLUMN_NAME_PASIEN, menu.name)
        values.put(COLUMN_DATEOFBIRTH_PASIEN, menu.dateOfBirth)
        values.put(COLUMN_NOHP_PASIEN, menu.noHp)

        val result = db.update(TABLE_AKUNPASIEN, values , COLUMN_EMAIL_PASIEN + " = ? ", arrayOf(menu.email.toString())).toLong()
        //show message
        if(result==(0).toLong()){
            Toast.makeText(context, "Edit Failed", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context, "Edit Success", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }
    fun editProfileDokter(menu:ProfileDokterModel){
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COLUMN_EMAIL_DOKTER, menu.email)
        values.put(COLUMN_NAME_DOKTER, menu.nama)
        values.put(COLUMN_SPECIALIS_DOKTER, menu.spesialis)
        values.put(COLUMN_RUMAHSAKIT, menu.rumahSakit)
        values.put(COLUMN_JADWAL_DOKTER, menu.jadwal)


        val result = db.update(TABLE_AKUNDOKTER, values , COLUMN_EMAIL_DOKTER + " = ? ", arrayOf(menu.email.toString())).toLong()
        //show message
        if(result==(0).toLong()){
            Toast.makeText(context, "Edit Failed", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context, "Edit Success", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }
}