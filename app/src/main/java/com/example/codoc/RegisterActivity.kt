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


        
        //input form
        val txtFullname:EditText = findViewById(R.id.fullnameInput)
        val txtEmail:EditText = findViewById(R.id.emailInput)
        val txtNoHp:EditText = findViewById(R.id.nomorInput)
        val txtPassword:EditText = findViewById(R.id.passwordInput)

        //instance button daftar
        val btnRegister:Button = findViewById(R.id.buttonDaftar)

        btnRegister.setOnClickListener {
            //object class databasehelper
            val databaseHelper = DatabaseHelper(this)
            //declare data
            val email:String = txtEmail.text.toString().trim()
            val name:String = txtFullname.text.toString().trim()
            val noHp:String = txtNoHp.text.toString().trim()
            val password:String = txtPassword.text.toString().trim()

            //check data -> email sudah terdaftar atau belum
            val data:String = databaseHelper.checkData(email)
            //jika belum terdaftar
            if(data == ""){ //data ==null
                //insert data
                databaseHelper.addAccount(
                    email,name,noHp,password)

                //show LoginActivity
                val intentlogin = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intentlogin)
            }else{
                //jika email telah terdaftar
                Toast.makeText(this@RegisterActivity,"Register failed." + "Your email already registered", Toast.LENGTH_SHORT).show()
            }
        }
    }
}