package com.example.codoc.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.codoc.DatabaseHelper
import com.example.codoc.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var dbHelper: DatabaseHelper

    //to retrieve data on ProfileActivity
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
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

        binding.buttonEdit.setOnClickListener {
            val intent = Intent(baseContext, EditProfileActivity::class.java)
            intent.putExtra("EMAIL", userEmail)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getUserData(userEmail: String) {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            DatabaseHelper.COLUMN_NAME,
            DatabaseHelper.COLUMN_DATEOFBIRTH,
            DatabaseHelper.COLUMN_EMAIL,
            DatabaseHelper.COLUMN_NOHP
        )

        val selection = "${DatabaseHelper.COLUMN_EMAIL} = ?"
        val selectionArgs = arrayOf(userEmail)

        val cursor = db.query(
            DatabaseHelper.TABLE_ACCOUNT,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        cursor.use {
            if (it.moveToFirst()) {
                val name = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME))
                val dateOfBirth = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATEOFBIRTH))
                val email = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL))
                val phone = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOHP))

                binding.namauser.text = "$name"
                binding.tanggaluser.text = "$dateOfBirth"
                binding.emailuser.text = "$email"
                binding.noTelpUser.text = "$phone"
            } else {
                Log.e("ProfileActivity", "No data found for user with email: $userEmail")
            }
        }
    }
}
