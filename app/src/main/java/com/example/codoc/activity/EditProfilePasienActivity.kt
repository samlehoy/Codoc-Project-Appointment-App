package com.example.codoc.activity

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
    companion object {
        var email = ""
        var name = ""
        var dateOfBirth = ""
        var noHp = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // untuk kalender
        editTglLahirEditText = findViewById(R.id.editTglLahir)

        // Set a click listener to show the DatePickerDialog when the EditText is clicked
        editTglLahirEditText.setOnClickListener {
            showDatePickerDialog()
        }

        // hide title bar
        supportActionBar?.hide()

        // instance
        val textEmail: TextInputEditText = findViewById(R.id.editemail)
        val textFullname: TextInputEditText = findViewById(R.id.editnama)
        val textLahir: EditText = findViewById(R.id.editTglLahir)
        val textNoHp: TextInputEditText = findViewById(R.id.editnomor)
        val btnUpdate: Button = findViewById(R.id.buttonUpdate)

        // Retrieve data from extras
        val intent = intent
        val emailExtra = intent.getStringExtra("EMAIL")

        // set data
        textEmail.setText(email)
        textFullname.setText(name)
        textLahir.setText(dateOfBirth)
        textNoHp.setText(noHp)
        // Set data to EditText fields
        textEmail.setText(emailExtra)

        btnUpdate.setOnClickListener {
            // object class databaseHelper
            val databaseHelper = DatabaseHelper(this)

            val email: String = textEmail.text.toString().trim()
            val name: String = textFullname.text.toString().trim()
            val dateOfBirth: String = textLahir.text.toString().trim()
            val noHp: String = textNoHp.text.toString().trim()

            val profilePasienModel = ProfilePasienModel(email, name, dateOfBirth, noHp)
            databaseHelper.editProfilePasien(profilePasienModel)

            // intent main activity
            val intent = Intent(this, HomePasienActivity::class.java)
            startActivity(intent)
        }
    }

    // untuk kalender
    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                // Update the EditText with the selected date
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)

                if (selectedDate < Calendar.getInstance()) {
                    // Update the EditText with the selected current or future date
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

    // untuk kalender
    private fun updateDateInView(calendar: Calendar) {
        val myFormat = "dd/MM/yyyy" // specify your format here
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        editTglLahirEditText.setText(sdf.format(calendar.time))
    }
}
