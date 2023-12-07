package com.example.codoc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class LoginPasienActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_pasien)

        supportActionBar?.hide()

        //button
        val btnMasuk: Button = findViewById(R.id.buttonMasuk)
        val btnDaftar: TextView = findViewById(R.id.textViewDaftar)
        //instance text
        val txtEmailLayout: TextInputLayout = findViewById(R.id.inputEmail)
        val txtPasswordLayout: TextInputLayout = findViewById(R.id.inputPassword)

        //event button Masuk/login
        btnMasuk.setOnClickListener {

            //instance
            val dbHelper = DatabaseHelper(this)

            // Access the underlying EditText from TextInputLayout
            val email = txtEmailLayout.editText?.text.toString().trim()
            val password = txtPasswordLayout.editText?.text.toString().trim()

            //check login
            val result: Boolean = dbHelper.checkLogin(email, password)
            if (result) {
                val intentLogin = Intent(this@LoginPasienActivity, HomeActivity::class.java)
                startActivity(intentLogin)
            } else {
                Toast.makeText(this, "Login Failed. Try Again", Toast.LENGTH_SHORT).show()
                txtEmailLayout.hint = "email"
                txtPasswordLayout.hint = "password"
            }
        }

        //event "Daftar"
        btnDaftar.setOnClickListener {
            val intentRegisterPasienActivity = Intent(this, RegisterPasienActivity::class.java)
            startActivity(intentRegisterPasienActivity)
        }
    }
}
