package com.example.codoc.activity.pasien

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.widget.Button
import android.widget.Toast
import com.example.codoc.DatabaseHelper
import com.example.codoc.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegisterPasienActivity : AppCompatActivity() {

    private lateinit var selectDateEditText: TextInputEditText
    private val calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_pasien)

        //untuk kalender
        selectDateEditText = findViewById(R.id.select_date)
        // Set a click listener to show the DatePickerDialog when the EditText is clicked
        selectDateEditText.setOnClickListener {
            showDatePickerDialog()
        }

        // Input form
        val txtFullname: TextInputLayout = findViewById(R.id.fullnameInput)
        val txtDateOfBirth: TextInputLayout = findViewById(R.id.jadwalInput)
        val txtEmail: TextInputLayout = findViewById(R.id.emailInput)
        val txtNoHp: TextInputLayout = findViewById(R.id.nomorInput)
        val txtPassword: TextInputLayout = findViewById(R.id.inputPassword)

        // Instance button daftar
        val btnRegister: Button = findViewById(R.id.buttonDaftar)

        // val untuk format nomor hp
        val inputNomorEdit: TextInputEditText = findViewById(R.id.InputNomorEdit)
        inputNomorEdit.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        btnRegister.setOnClickListener {
            // Object class DatabaseHelper
            val databaseHelper = DatabaseHelper(this)
            // Declare data
            val email: String = txtEmail.editText?.text.toString().trim()
            val name: String = txtFullname.editText?.text.toString().trim()
            val dateOfBirth: String = txtDateOfBirth.editText?.text.toString().trim()
            val noHp: String = txtNoHp.editText?.text.toString().trim()
            val password: String = txtPassword.editText?.text.toString().trim()


            // Check data -> email sudah terdaftar atau belum
            val data: String = databaseHelper.checkDataPasien(email)
            // Jika belum terdaftar
            if (data == "") {
                // Insert data
                databaseHelper.addAccountPasien(email, name, dateOfBirth, noHp, password)

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

    //untuk kalender
    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                // Update the TextInputEditText with the selected date
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)

                if (selectedDate < Calendar.getInstance()) {
                    // Update the TextInputEditText with the selected current or future date
                    updateDateInView(selectedDate)
                } else {
                    // Show a message if the selected date is in the past
                    // This can be customized based on your requirements
                    // For example, you might want to show a message and not update the date
                    // Or you can show a Toast message
                    Toast.makeText(this, "Periksa kembali tanggal lahir anda", Toast.LENGTH_SHORT).show()
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Show the DatePickerDialog
        datePickerDialog.show()
    }
    //untuk kalender
    private fun updateDateInView(calendar: Calendar) {
        val myFormat = "dd/MM/yyyy" // specify your format here
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        selectDateEditText.setText(sdf.format(calendar.time))
    }
}
