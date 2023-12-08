package com.example.codoc.activity

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.codoc.DatabaseHelper
import com.example.codoc.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegisterDokterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_dokter)

        val spesialDropdown = findViewById<AutoCompleteTextView>(R.id.spesial_dropdown)
        val spesialOptions = arrayOf("Umum", "Mata", "THT", "Kulit", "Gigi", "Kandungan", "Penyakit Dalam") // Gantilah dengan opsi yang sesuai
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, spesialOptions)
        spesialDropdown.setAdapter(adapter)

        // Opsional: Tambahkan listener untuk menangani pemilihan opsi
        spesialDropdown.setOnItemClickListener { _, _, position, _ ->
            val selectedOption = adapter.getItem(position).toString()
            // Lakukan sesuatu dengan opsi yang dipilih
        }

        // Input form
        val txtEmail: TextInputLayout = findViewById(R.id.emailInput)
        val txtFullname: TextInputLayout = findViewById(R.id.fullnameInput)
        val txtSpecialis: TextInputLayout = findViewById(R.id.menu_spesial)
        val txtPassword: TextInputLayout = findViewById(R.id.inputPassword)

        // Instance button daftar
        val btnRegister: Button = findViewById(R.id.buttonDaftar)

        btnRegister.setOnClickListener {
            // Object class DatabaseHelper
            val databaseHelper = DatabaseHelper(this)
            // Declare data
            val email: String = txtEmail.editText?.text.toString().trim()
            val name: String = txtFullname.editText?.text.toString().trim()
            val specialis: String = txtSpecialis.editText?.text.toString().trim()
            val password: String = txtPassword.editText?.text.toString().trim()


            // Check data -> email sudah terdaftar atau belum
            val data: String = databaseHelper.checkDataDokter(email)
            // Jika belum terdaftar
            if (data == "") {
                // Insert data
                databaseHelper.addAccountDokter(email, name, specialis, password)

                // Show LoginActivity
                val intentLogin = Intent(this@RegisterDokterActivity, LoginPasienActivity::class.java)
                startActivity(intentLogin)
            } else {
                // Jika email telah terdaftar
                Toast.makeText(
                    this@RegisterDokterActivity,
                    "Register failed. Your email already registered",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
