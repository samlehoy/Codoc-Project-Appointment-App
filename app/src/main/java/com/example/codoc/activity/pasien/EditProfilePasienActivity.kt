package com.example.codoc.activity.pasien

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.codoc.DatabaseHelper
import com.example.codoc.R
import com.example.codoc.model.ProfilePasienModel
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditProfilePasienActivity : AppCompatActivity() {

    private lateinit var editTglLahirEditText: EditText
    private val calendar = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // Initialize views
        editTglLahirEditText = findViewById(R.id.editTglLahir)
        val textEmail: TextInputEditText = findViewById(R.id.editemail)
        val textFullname: TextInputEditText = findViewById(R.id.editnama)
        val textLahir: EditText = findViewById(R.id.editTglLahir)
        val textNoHp: TextInputEditText = findViewById(R.id.editnomor)
        val btnUpdate: Button = findViewById(R.id.buttonUpdate)

        // Set onClickListener for the date picker
        editTglLahirEditText.setOnClickListener {
            showDatePickerDialog()
        }

        // Hide title bar
        supportActionBar?.hide()

        // Retrieve data from extras
        val intent = intent
        val emailExtra = intent.getStringExtra("EMAIL")
        val nameExtra = intent.getStringExtra("NAME")
        val dobExtra = intent.getStringExtra("DOB")
        val phoneExtra = intent.getStringExtra("PHONE")

        // Set data to EditText fields
        textEmail.setText(emailExtra)
        textFullname.setText(nameExtra)
        textLahir.setText(dobExtra)
        textNoHp.setText(phoneExtra)

        // Set onClickListener for the update button
        btnUpdate.setOnClickListener {
            updateProfile()
        }
    }

    private fun updateProfile() {
        val databaseHelper = DatabaseHelper(this)

        val email: String = findViewById<TextInputEditText>(R.id.editemail).text.toString().trim()
        val name: String = findViewById<TextInputEditText>(R.id.editnama).text.toString().trim()
        val dateOfBirth: String = findViewById<EditText>(R.id.editTglLahir).text.toString().trim()
        val noHp: String = findViewById<TextInputEditText>(R.id.editnomor).text.toString().trim()

        val profilePasienModel = ProfilePasienModel(email, name, dateOfBirth, noHp)
        databaseHelper.editProfilePasien(profilePasienModel)

        // Update companion object values
        ProfilePasienActivity.email = email
        ProfilePasienActivity.name = name
        ProfilePasienActivity.ttl = dateOfBirth
        ProfilePasienActivity.nohp = noHp

        // Intent to main activity
        val intent = Intent(this, HomePasienActivity::class.java)
        startActivity(intent)
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)

                if (selectedDate < Calendar.getInstance()) {
                    updateDateInView(selectedDate)
                } else {
                    Toast.makeText(this, "Periksa kembali tanggal lahir anda", Toast.LENGTH_SHORT).show()
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    private fun updateDateInView(calendar: Calendar) {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        editTglLahirEditText.setText(sdf.format(calendar.time))
    }
}
