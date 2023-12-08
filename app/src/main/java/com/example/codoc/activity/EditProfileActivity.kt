package com.example.codoc.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.codoc.DatabaseHelper
import com.example.codoc.R
import com.example.codoc.model.ProfileModel

class EditProfileActivity : AppCompatActivity() {
    companion object{
        var email = ""
        var name = ""
        var dateOfBirth = ""
        var noHp = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        //hide title bar
        getSupportActionBar()?.hide()

        // Retrieve data from extras
        val intent = intent
        val emailExtra = intent.getStringExtra("EMAIL")
        val nameExtra = intent.getStringExtra("NAME")
        val dateOfBirthExtra = intent.getStringExtra("DATE_OF_BIRTH")
        val noHpExtra = intent.getStringExtra("NO_HP")
        //instance
        val textEmail : EditText = findViewById(R.id.editEmail)
        val textFullname : EditText = findViewById(R.id.editnama)
        val textLahir : EditText = findViewById(R.id.editTglLahir)
        val textNoHp : EditText = findViewById(R.id.editnomor)
        val btnUpdate : Button = findViewById(R.id.buttonUpdate)

        // Set data to EditText fields
        textEmail.setText(emailExtra)
        textFullname.setText(nameExtra)
        textLahir.setText(dateOfBirthExtra)
        textNoHp.setText(noHpExtra)
        //set data
        textEmail.setText(email)
        textFullname.setText(name)
        textLahir.setText(dateOfBirth)
        textNoHp.setText(noHp)


        btnUpdate.setOnClickListener{
            //object class databaseHelper
            val databaseHelper = DatabaseHelper(this)

            val email: String = textEmail.text.toString().trim()
            val name: String = textFullname.text.toString().trim()
            val dateOfBirth: String = textLahir.text.toString().trim()
            val noHp: String = textNoHp.text.toString().trim()

            val profileModel = ProfileModel(email , name, dateOfBirth, noHp)
            databaseHelper.editProfile(profileModel)

            //intent main activity
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)

        }
    }
}