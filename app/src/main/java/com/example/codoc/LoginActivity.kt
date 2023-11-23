package com.example.codoc


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        getSupportActionBar()?.hide()

        //instance
        val btnMasuk:Button = findViewById(R.id.buttonMasuk)
        val btnDaftar:TextView = findViewById(R.id.textViewDaftar)
        val txtEmail:EditText = findViewById(R.id.inputEmail)
        val txtPassword:EditText = findViewById(R.id.inputPassword)

        //event button Masuk/login
        btnMasuk.setOnClickListener {
            val dbHelper = DatabaseHelper(this)

            //check data
            val data: String = dbHelper.checkData("stevi.ema@amikom.ac.id")
            Toast.makeText(this@LoginActivity, "Result : " + data, Toast.LENGTH_SHORT).show()
            if (data == "") {
                //insert data
                dbHelper.addAccount("stevi.ema@amikom.ac.id", "Stevi Ema W", "Cashier", "12345")
            }
            val email = txtEmail.text.toString().trim()
            val password = txtPassword.text.toString().trim()

            //check login
            val result: Boolean =
                dbHelper.checkLogin(txtEmail.text.toString(), txtPassword.text.toString())
            if (result) {
                val intentLogin = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intentLogin)
            } else {
                Toast.makeText(this, "Login Failed. Try Again", Toast.LENGTH_SHORT).show()
                txtEmail.hint = "email"
                txtPassword.hint = "password"
            }
        }
        //event "Daftar"
        btnDaftar.setOnClickListener {
            val intentRegisterActivity = Intent(this,RegisterActivity::class.java)
            startActivity(intentRegisterActivity)
        }

    }
}