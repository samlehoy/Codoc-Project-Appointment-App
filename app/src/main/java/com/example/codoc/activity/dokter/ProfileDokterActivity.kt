package com.example.codoc.activity.dokter

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.codoc.DatabaseHelper
import com.example.codoc.databinding.ActivityProfileDokterBinding

class ProfileDokterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileDokterBinding
    private lateinit var dbHelper: DatabaseHelper

    //to retrieve data on ProfileActivity
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDokterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        //to retrieve data on ProfileActivity
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("user_email", "")
        Log.d("ProfileActivity", "User Email from SharedPreferences: $userEmail")
        if (userEmail.isNullOrBlank()) {
            // Handle the case where user email is not available (user not logged in)
            Log.e("ProfileActivity", "User email not found.")
        } else {
            getUserData(userEmail)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getUserData(userEmail: String) {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            DatabaseHelper.COLUMN_NAME_DOKTER,
            DatabaseHelper.COLUMN_SPECIALIS_DOKTER,
            DatabaseHelper.COLUMN_ALAMAT_DOKTER,
            DatabaseHelper.COLUMN_EMAIL_DOKTER,
            DatabaseHelper.COLUMN_NOHP_DOKTER
        )

        val selection = "${DatabaseHelper.COLUMN_EMAIL_DOKTER} = ?"
        val selectionArgs = arrayOf(userEmail)

        val cursor = db.query(
            DatabaseHelper.TABLE_AKUNDOKTER,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        cursor.use {
            if (it.moveToFirst()) {
                val name = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME_DOKTER))
                val spesialis = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SPECIALIS_DOKTER))
                val alamat = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ALAMAT_DOKTER))
                val email = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL_DOKTER))
                val noHp = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOHP_DOKTER))

                binding.namauser.text = "$name"
                binding.spesialisDokter.text = "$spesialis"
                binding.rumahsakit.text = "$alamat"
                binding.emailDokter.text = "$email"
                binding.noHpDokter.text = "$noHp"
            } else {
                Log.e("ProfileActivity", "No data found for user with email: $userEmail")
            }
        }
    }
}