package com.example.codoc


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //instance button daftar
        val btnRegister:Button = findViewById(R.id.buttonDaftar)
        
        //input form
        val txtFullname:EditText = findViewById(R.id.fullnameInput)
        val txtEmail:EditText = findViewById(R.id.emailInput)
        val txtNoHp:EditText = findViewById(R.id.nomorInput)
        val txtPassword:EditText = findViewById(R.id.passwordInput)
        val txtRePassword:EditText = findViewById(R.id.RepasswordInput)
        val logModel = LoginModel()



        btnRegister.setOnClickListener {
            //object class databaseHelper
            val databaseHelper = DatabaseHelper(this)
            //declare data
            val fullname: String = txtFullname.text.toString().trim()
            val email: String = txtEmail.text.toString().trim()
            val noHp: String = txtNoHp.text.toString().trim()
            val password: String = txtPassword.text.toString().trim()
            val rePassword: String = txtRePassword.text.toString().trim()

            //check data -> email sudah terdaftar atau belum
            //FIXED REGISTER (GANTI String ke String?)
            val data:String? = databaseHelper.checkData(email)
            //jika belum terdaftar
            if(data == null){
                //insert data
                databaseHelper.addAccount(
                    fullname,email,noHp,password)

                //show LoginActivity
                val intentLogin = Intent(this@RegisterActivity,
                    LoginActivity::class.java)
                startActivity(intentLogin)
            }else{
                //jika email telah terdaftar
                Toast.makeText(this@RegisterActivity, "Register failed." +
                        "your email already registered", Toast.LENGTH_SHORT).show()
            }
        }
    }
}