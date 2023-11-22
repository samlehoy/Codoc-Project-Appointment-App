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
        val logModel = LoginModel()


        //event button Masuk/login
        btnMasuk.setOnClickListener {
            //input
            val dbHelper = DatabaseHelper(this)
            var result:Boolean = dbHelper.checkLogin(txtEmail.text.toString(),txtPassword.text.toString())
            if (result){
                val intentLogin = Intent( this@LoginActivity,
                    ChatActivity::class.java)
                startActivity(intentLogin)
            }else{
                Toast.makeText(this@LoginActivity,"Login failed. Try Again",
                    Toast.LENGTH_SHORT).show()
                txtEmail.hint = "email"
                txtPassword.hint = "password"
            }

            //check data
            //FIXED REGISTER (GANTI String ke String?)
            val data: String? = dbHelper.checkData(txtEmail.text.toString())
            Toast.makeText(this@LoginActivity, "Result : " + data,
                Toast.LENGTH_SHORT).show()
            if(data==null){
                //insert data
                dbHelper.addAccount(txtEmail.text.toString(), "Native Muttaqien", "Cashier", txtPassword.text.toString())
            }
        }
        //event "Daftar"
        btnDaftar.setOnClickListener {
            val intentRegisterActivity = Intent(this,RegisterActivity::class.java)
            startActivity(intentRegisterActivity)
        }

    }
}