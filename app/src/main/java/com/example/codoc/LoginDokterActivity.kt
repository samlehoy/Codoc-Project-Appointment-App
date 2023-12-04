package com.example.codoc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class LoginDokterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_dokter)

        supportActionBar?.hide()

        //instance
        val btnMasuk: Button = findViewById(R.id.buttonMasuk)
        val btnDaftar: TextView = findViewById(R.id.textViewDaftar)
        val txtEmailLayout: TextInputLayout = findViewById(R.id.inputEmail)
        val txtPasswordLayout: TextInputLayout = findViewById(R.id.inputPassword)

        //event button Masuk/login
        btnMasuk.setOnClickListener {
            val dbHelper = DatabaseHelper(this)

            //check data
            val data: String = dbHelper.checkData("stevi.ema@amikom.ac.id")
            Toast.makeText(this@LoginDokterActivity, "Result : " + data, Toast.LENGTH_SHORT).show()
            if (data == "") {
                //insert data
                dbHelper.addAccount("stevi.ema@amikom.ac.id", "Stevi Ema W", "Cashier", "12345")
            }

            // Access the underlying EditText from TextInputLayout
            val email = txtEmailLayout.editText?.text.toString().trim()
            val password = txtPasswordLayout.editText?.text.toString().trim()

            //check login
            val result: Boolean = dbHelper.checkLogin(email, password)
            if (result) {
                val intentLogin = Intent(this@LoginDokterActivity, HomeActivity::class.java)
                startActivity(intentLogin)
            } else {
                Toast.makeText(this, "Login Failed. Try Again", Toast.LENGTH_SHORT).show()
                txtEmailLayout.hint = "email"
                txtPasswordLayout.hint = "password"
            }
        }

        //event "Daftar"
        btnDaftar.setOnClickListener {
            val intentRegisterPasienActivity = Intent(this, RegisterDokterActivity::class.java)
            startActivity(intentRegisterPasienActivity)
        }
    }
}
