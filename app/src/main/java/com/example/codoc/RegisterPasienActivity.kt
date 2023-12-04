package com.example.codoc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class RegisterPasienActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_pasien)

        // Input form
        val txtFullname: TextInputLayout = findViewById(R.id.fullnameInput)
        val txtEmail: TextInputLayout = findViewById(R.id.emailInput)
        val txtNoHp: TextInputLayout = findViewById(R.id.nomorInput)
        val txtPassword: TextInputLayout = findViewById(R.id.inputPassword)

        // Instance button daftar
        val btnRegister: Button = findViewById(R.id.buttonDaftar)

        btnRegister.setOnClickListener {
            // Object class DatabaseHelper
            val databaseHelper = DatabaseHelper(this)
            // Declare data
            val email: String = txtEmail.editText?.text.toString().trim()
            val name: String = txtFullname.editText?.text.toString().trim()
            val noHp: String = txtNoHp.editText?.text.toString().trim()
            val password: String = txtPassword.editText?.text.toString().trim()

            // Check data -> email sudah terdaftar atau belum
            val data: String = databaseHelper.checkData(email)
            // Jika belum terdaftar
            if (data == "") {
                // Insert data
                databaseHelper.addAccount(email, name, noHp, password)

                // Show LoginActivity
                val intentLogin = Intent(this@RegisterPasienActivity, LoginPasienActivity::class.java)
                startActivity(intentLogin)
            } else {
                // Jika email telah terdaftar
                Toast.makeText(
                    this@RegisterPasienActivity,
                    "Register failed. Your email already registered",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
