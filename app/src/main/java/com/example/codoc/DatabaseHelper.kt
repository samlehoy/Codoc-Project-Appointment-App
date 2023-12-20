package com.example.codoc

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.codoc.activity.dokter.ProfileDokterActivity
import com.example.codoc.activity.pasien.ProfilePasienActivity
import com.example.codoc.model.MyJanjiModel
import com.example.codoc.model.ProfileDokterModel
import com.example.codoc.model.ProfilePasienModel


class DatabaseHelper(var context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {
    companion object {
        const val DATABASE_NAME = "Codoc"
        const val DATABASE_VERSION = 4 // Incremented the version number

        // Table names
        const val TABLE_AKUNPASIEN = "akunpasien"
        const val TABLE_AKUNDOKTER = "akundokter"
        const val TABLE_MYJANJI = "myjanji"

        // Columns for account pasien
        const val COLUMN_EMAIL_PASIEN = "email_pasien"
        const val COLUMN_NAME_PASIEN = "name_pasien"
        const val COLUMN_DATEOFBIRTH_PASIEN = "date_of_birth"
        const val COLUMN_NOHP_PASIEN = "nohp_pasien"
        const val COLUMN_PASSWORD_PASIEN = "password_pasien"

        // Columns for account dokter
        const val COLUMN_EMAIL_DOKTER = "email_dokter"
        const val COLUMN_NAME_DOKTER = "name_dokter"
        const val COLUMN_SPECIALIS_DOKTER = "specialis_dokter"
        const val COLUMN_ALAMAT_DOKTER = "alamat"
        const val COLUMN_NOHP_DOKTER = "nohp_dokter"
        const val COLUMN_PASSWORD_DOKTER = "password_dokter"

        // Columns for MYJANJI table
        const val COLUMN_ID_JANJI = "id_janji"
        const val COLUMN_NAMA_DOKTER_JANJI = "name_dokter"
        const val COLUMN_EMAIL_DOKTER_JANJI = "email_dokter"
        const val COLUMN_EMAIL_PASIEN_JANJI = "email_pasien"
        const val COLUMN_TANGGAL_JANJI = "tanggal"
        const val COLUMN_JAM_JANJI = "jam"

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
            + COLUMN_ALAMAT_DOKTER + " TEXT, "
            + COLUMN_NOHP_DOKTER + " TEXT, "
            + COLUMN_PASSWORD_DOKTER + " TEXT)")

    // Create table MYJANJI sql query
    private val CREATE_MYJANJI_TABLE = ("CREATE TABLE IF NOT EXISTS $TABLE_MYJANJI ("
            + "$COLUMN_ID_JANJI INTEGER PRIMARY KEY, "
            + "$COLUMN_NAMA_DOKTER_JANJI TEXT, "
            + "$COLUMN_EMAIL_DOKTER_JANJI TEXT, "
            + "$COLUMN_EMAIL_PASIEN_JANJI TEXT, "
            + "$COLUMN_TANGGAL_JANJI TEXT, "
            + "$COLUMN_JAM_JANJI TEXT, "
            + "FOREIGN KEY($COLUMN_EMAIL_DOKTER_JANJI) REFERENCES $TABLE_AKUNDOKTER($COLUMN_EMAIL_DOKTER), "
            + "FOREIGN KEY($COLUMN_EMAIL_PASIEN_JANJI) REFERENCES $TABLE_AKUNPASIEN($COLUMN_EMAIL_PASIEN))")

    // Drop table account sql query
    private val DROP_ACCOUNT_TABLE = "DROP TABLE IF EXISTS $TABLE_AKUNPASIEN"
    private val DROP_ACCOUNT_TABLE_DOKTER = "DROP TABLE IF EXISTS $TABLE_AKUNDOKTER"
    // Drop table MYJANJI sql query
    private val DROP_MYJANJI_TABLE = "DROP TABLE IF EXISTS $TABLE_MYJANJI"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_ACCOUNT_TABLE)
        db.execSQL(CREATE_ACCOUNT_TABLE_DOKTER)
        db.execSQL(CREATE_MYJANJI_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_ACCOUNT_TABLE)
        db.execSQL(DROP_ACCOUNT_TABLE_DOKTER)
        db.execSQL(DROP_MYJANJI_TABLE)
        onCreate(db)
    }

    //CHECK LOGIN PASIEN
    @SuppressLint("Range")
    fun checkLoginPasien(email: String, password: String): Boolean {
        println("Email: $email, Password: $password")
        try {
            val columns = arrayOf(COLUMN_EMAIL_PASIEN, COLUMN_NAME_PASIEN, COLUMN_DATEOFBIRTH_PASIEN, COLUMN_NOHP_PASIEN,  COLUMN_PASSWORD_PASIEN)
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
            val result : Boolean
            //check data available or not
            if(cursorCount > 0){
                result = true
                //set data
                if(cursor.moveToFirst()){
                    FragmentPasienHome.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PASIEN))

                    ProfilePasienActivity.email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_PASIEN))
                    ProfilePasienActivity.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PASIEN))
                    ProfilePasienActivity.ttl = cursor.getString(cursor.getColumnIndex(COLUMN_DATEOFBIRTH_PASIEN))
                    ProfilePasienActivity.nohp = cursor.getString(cursor.getColumnIndex(COLUMN_NOHP_PASIEN))
                }
            }
            else {
                result = false
            }
            cursor.close()
            db.close()
            return result

            return cursorCount > 0
        } catch (e: Exception) {
            // Log the exception or handle it as needed
            e.printStackTrace()
            return false
        }
    }

    //CHECK LOGIN DOKTER
    @SuppressLint("Range")
    fun checkLoginDokter(email: String, password: String): Boolean {
        println("Email: $email, Password: $password")
        try {
            val columns = arrayOf(COLUMN_NAME_DOKTER, COLUMN_EMAIL_DOKTER, COLUMN_SPECIALIS_DOKTER, COLUMN_ALAMAT_DOKTER, COLUMN_NOHP_DOKTER, COLUMN_PASSWORD_DOKTER)
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
            val result : Boolean
            //check data available or not
            if(cursorCount > 0){
                result = true
                //set data
                if(cursor.moveToFirst()){
                    FragmentDokterHome.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DOKTER))

                    ProfileDokterActivity.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DOKTER))
                    ProfileDokterActivity.spesialis = cursor.getString(cursor.getColumnIndex(COLUMN_SPECIALIS_DOKTER))
                    ProfileDokterActivity.alamatRs = cursor.getString(cursor.getColumnIndex(COLUMN_ALAMAT_DOKTER))
                    ProfileDokterActivity.email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_DOKTER))
                    ProfileDokterActivity.nohp = cursor.getString(cursor.getColumnIndex(COLUMN_NOHP_DOKTER))
                }
            }
            else {
                result = false
            }

            cursor.close()
            db.close()
            return result

            return cursorCount > 0
        } catch (e: Exception) {
            // Log the exception or handle it as needed
            e.printStackTrace()
            return false
        }
    }

    //ADD ACCOUNT UNTUK REGISTER PASIEN
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

    //ADD ACCOUNT UNTUK REGISTER DOKTER
    fun addAccountDokter(email: String, name: String, specialis: String, alamat: String, noHp: String, password: String) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_EMAIL_DOKTER, email)
        values.put(COLUMN_NAME_DOKTER, name)
        values.put(COLUMN_SPECIALIS_DOKTER, specialis)
        values.put(COLUMN_ALAMAT_DOKTER, alamat)
        values.put(COLUMN_NOHP_DOKTER, noHp)
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

    //DATA CHECK UNTUK REGISTER APAKAH EMAIL SUDAH ADA ATAU BLUM
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

    //DATA CHECK UNTUK REGISTER APAKAH EMAIL SUDAH ADA ATAU BLUM
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

    //FUNGSI UNTUK UPDATE DATA PASIEN PADA ProfilePasienActivity
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

    //FUNGSI UNTUK MERETRIEVE DATA DOKTER DARI DATABASE LALU DILETAKKAN PADA FragmentPasienHome
    fun getAllDoctors(): List<ProfileDokterModel> {
        val doctorsList = mutableListOf<ProfileDokterModel>()
        val db = this.readableDatabase

        val columns = arrayOf(
            COLUMN_NAME_DOKTER,
            COLUMN_SPECIALIS_DOKTER,
            COLUMN_ALAMAT_DOKTER,
            COLUMN_EMAIL_DOKTER,
            COLUMN_NOHP_DOKTER
        )

        val cursor = db.query(
            TABLE_AKUNDOKTER,
            columns,
            null,
            null,
            null,
            null,
            null
        )

        cursor.use {
            while (it.moveToNext()) {
                val name = it.getString(it.getColumnIndexOrThrow(COLUMN_NAME_DOKTER))
                val specialty = it.getString(it.getColumnIndexOrThrow(COLUMN_SPECIALIS_DOKTER))
                val address = it.getString(it.getColumnIndexOrThrow(COLUMN_ALAMAT_DOKTER))
                val email = it.getString(it.getColumnIndexOrThrow(COLUMN_EMAIL_DOKTER))
                val phoneNumber = it.getString(it.getColumnIndexOrThrow(COLUMN_NOHP_DOKTER))

                val doctor = ProfileDokterModel(name, specialty, address, email, phoneNumber)
                doctorsList.add(doctor)
            }
        }

        db.close()
        return doctorsList
    }

    //FUNGSI SORTING BY HORIZONTALSCROLL VIEW PADA FragmentPasienHome
    fun getDoctorsBySpecialty(specialty: String): List<ProfileDokterModel> {
        val doctorsList = mutableListOf<ProfileDokterModel>()
        val db = this.readableDatabase

        val columns = arrayOf(
            COLUMN_NAME_DOKTER,
            COLUMN_SPECIALIS_DOKTER,
            COLUMN_ALAMAT_DOKTER,
            COLUMN_EMAIL_DOKTER,
            COLUMN_NOHP_DOKTER
        )

        val selection = "$COLUMN_SPECIALIS_DOKTER = ?"
        val selectionArgs = arrayOf(specialty)

        val cursor = db.query(
            TABLE_AKUNDOKTER,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        cursor.use {
            while (it.moveToNext()) {
                val name = it.getString(it.getColumnIndexOrThrow(COLUMN_NAME_DOKTER))
                val doctorSpecialty = it.getString(it.getColumnIndexOrThrow(COLUMN_SPECIALIS_DOKTER))
                val address = it.getString(it.getColumnIndexOrThrow(COLUMN_ALAMAT_DOKTER))
                val email = it.getString(it.getColumnIndexOrThrow(COLUMN_EMAIL_DOKTER))
                val phoneNumber = it.getString(it.getColumnIndexOrThrow(COLUMN_NOHP_DOKTER))

                val doctor = ProfileDokterModel(name, doctorSpecialty, address,email , phoneNumber)
                doctorsList.add(doctor)
            }
        }

        db.close()
        return doctorsList
    }

    //FUNGSI SEARCH PADA FragmentPasienHome
    fun searchDoctors(query: String): List<ProfileDokterModel> {
        val doctorsList = mutableListOf<ProfileDokterModel>()
        val db = this.readableDatabase

        val columns = arrayOf(
            COLUMN_NAME_DOKTER,
            COLUMN_SPECIALIS_DOKTER,
            COLUMN_ALAMAT_DOKTER,
            COLUMN_EMAIL_DOKTER,
            COLUMN_NOHP_DOKTER
        )

        val selection = "$COLUMN_NAME_DOKTER LIKE ? OR $COLUMN_SPECIALIS_DOKTER LIKE ? OR $COLUMN_NOHP_DOKTER LIKE ?"
        val selectionArgs = arrayOf("%$query%", "%$query%", "%$query%")

        val cursor = db.query(
            TABLE_AKUNDOKTER,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        cursor.use {
            while (it.moveToNext()) {
                val name = it.getString(it.getColumnIndexOrThrow(COLUMN_NAME_DOKTER))
                val specialty = it.getString(it.getColumnIndexOrThrow(COLUMN_SPECIALIS_DOKTER))
                val address = it.getString(it.getColumnIndexOrThrow(COLUMN_ALAMAT_DOKTER))
                val email = it.getString(it.getColumnIndexOrThrow(COLUMN_EMAIL_DOKTER))
                val phoneNumber = it.getString(it.getColumnIndexOrThrow(COLUMN_NOHP_DOKTER))

                val doctor = ProfileDokterModel(name, specialty, address, email, phoneNumber)
                doctorsList.add(doctor)
            }
        }
        db.close()
        return doctorsList
    }

    fun updateJanji(janji:MyJanjiModel){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID_JANJI, janji.id_janji)
        values.put(COLUMN_TANGGAL_JANJI, janji.tanggalJanji)
        values.put(COLUMN_JAM_JANJI, janji.jamJanji)


        val result = db.update(
            TABLE_MYJANJI,
            values,
            "$COLUMN_ID_JANJI = ?",
            arrayOf(janji.id_janji)
        )
        //show message
        if (result.toLong() ==(0).toLong()){
            Toast.makeText(context, "Update menu Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Update menu Success",Toast.LENGTH_SHORT).show()
        }
        db.close()
    }
    fun deleteJanji(id: String){
        val db = this.writableDatabase

        val result = db.delete(TABLE_MYJANJI, COLUMN_ID_JANJI + " = ? " , arrayOf(id)).toLong()
        //show message
        if (result==(0).toLong()){
            Toast.makeText(context, "Delete menu Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Delete menu Success",Toast.LENGTH_SHORT).show()
        }
        db.close()
    }
    fun saveAppointment(namaDokter:String, emailDokter: String, emailPasien: String, tanggal: String, jam: String): Boolean {
        try {
            // Dapatkan email pasien dari data yang sedang digunakan (misalnya, dari sesi)
            val emailPasien = ProfilePasienActivity.email

            // Gantilah email dokter sesuai dengan data yang Anda punya (misalnya, dari data dokter yang dipilih)
            val emailDokter = emailDokter // Gantilah dengan email dokter yang sesuai

            val db = this.writableDatabase
            val values = ContentValues()

            values.put(COLUMN_NAMA_DOKTER_JANJI, namaDokter)
            values.put(COLUMN_EMAIL_DOKTER_JANJI, emailDokter)
            values.put(COLUMN_EMAIL_PASIEN_JANJI, emailPasien)
            values.put(COLUMN_TANGGAL_JANJI, tanggal)
            values.put(COLUMN_JAM_JANJI, jam)

            val result = db.insert(TABLE_MYJANJI, null, values)

            db.close()

            return result != -1L
        } catch (e: Exception) {
            // Log the exception or handle it as needed
            e.printStackTrace()
            return false
        }
    }

    fun getDataJanjiPasien(email: String): List<MyJanjiModel> {
        val janjiList = mutableListOf<MyJanjiModel>()
        val db = this.readableDatabase
        val columns = arrayOf(
            COLUMN_ID_JANJI ,
            COLUMN_NAMA_DOKTER_JANJI ,
            COLUMN_EMAIL_DOKTER_JANJI ,
            COLUMN_EMAIL_PASIEN_JANJI ,
            COLUMN_TANGGAL_JANJI ,
            COLUMN_JAM_JANJI
        )

        val selection = "$COLUMN_EMAIL_PASIEN = ?"
        val selectionArgs = arrayOf(email)

        val cursor = db.query(
            TABLE_MYJANJI,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        cursor.use {
            while (it.moveToNext()) {
                val id_janji = it.getString(it.getColumnIndexOrThrow(COLUMN_ID_JANJI))
                val namaDokter = it.getString(it.getColumnIndexOrThrow(COLUMN_NAMA_DOKTER_JANJI))
                val emailPasien = it.getString(it.getColumnIndexOrThrow(COLUMN_EMAIL_PASIEN_JANJI))
                val emailDokter = it.getString(it.getColumnIndexOrThrow(COLUMN_EMAIL_DOKTER_JANJI))
                val tanggalJanji = it.getString(it.getColumnIndexOrThrow(COLUMN_TANGGAL_JANJI))
                val jamJanji = it.getString(it.getColumnIndexOrThrow(COLUMN_JAM_JANJI))

                val janji = MyJanjiModel(id_janji, namaDokter,emailPasien, emailDokter, tanggalJanji, jamJanji)
                janjiList.add(janji)
            }
        }

        db.close()
        return janjiList
    }


}