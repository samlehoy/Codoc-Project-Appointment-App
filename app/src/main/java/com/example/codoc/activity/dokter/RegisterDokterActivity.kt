package com.example.codoc.activity.dokter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import com.example.codoc.DatabaseHelper
import com.example.codoc.R
import com.google.android.material.textfield.TextInputLayout

class RegisterDokterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_dokter)

        //untuk dropdown spesialis
        val spesialDropdown = findViewById<AutoCompleteTextView>(R.id.spesial_dropdown)
        val spesialOptions = arrayOf("Umum", "THT", "Kulit", "Gigi", "Penyakit Dalam", "Kandungan", "Saraf")
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, spesialOptions)
        spesialDropdown.setAdapter(adapter)

        //Opsional: Tambahkan listener untuk menangani pemilihan opsi
        spesialDropdown.setOnItemClickListener { _, _, position, _ ->
            val selectedOption = adapter.getItem(position).toString()
            // Lakukan sesuatu dengan opsi yang dipilih
        }

        // Input form
        val txtEmail: TextInputLayout = findViewById(R.id.emailInput)
        val txtFullname: TextInputLayout = findViewById(R.id.fullnameInput)
        val txtSpecialis: TextInputLayout = findViewById(R.id.menu_spesial)
        val txtAlamat: TextInputLayout = findViewById(R.id.alamatInput)
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
            val specialis: String = txtSpecialis.editText?.text.toString().trim()
            val alamat: String = txtAlamat.editText?.text.toString().trim()
            val noHp: String = txtNoHp.editText?.text.toString().trim()
            val password: String = txtPassword.editText?.text.toString().trim()


            // Check data -> email sudah terdaftar atau belum
            val data: String = databaseHelper.checkDataDokter(email)
            // Jika belum terdaftar
            if (data == "") {
                // Insert data
                databaseHelper.addAccountDokter(email, name, specialis, alamat, noHp ,password)
                // Show LoginActivity
                val intentLogin = Intent(this@RegisterDokterActivity, LoginDokterActivity::class.java)
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
