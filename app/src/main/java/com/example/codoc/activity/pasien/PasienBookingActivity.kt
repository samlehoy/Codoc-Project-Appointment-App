package com.example.codoc.activity.pasien

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.codoc.R
import com.example.codoc.DatabaseHelper
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class PasienBookingActivity : AppCompatActivity() {

    private lateinit var selectDateEditText: TextInputEditText
    private val calendar = Calendar.getInstance()
    private val databaseHelper = DatabaseHelper(this)
    private lateinit var jamDropdown: AutoCompleteTextView
    private lateinit var buttonBuatJanji: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        initializeViews()
        setupJamDropdown()
        setupTanggal()
        setupButtonBuatJanji()
    }

    private fun initializeViews() {
        selectDateEditText = findViewById(R.id.select_date)
        jamDropdown = findViewById(R.id.jam_dropdown)
        buttonBuatJanji = findViewById(R.id.buttonbuatjanji)
    }

    private fun setupJamDropdown() {
        val jamOptions = arrayOf("09:00 - 11:00", "11:00 - 13:00", "13:00 - 15:00", "15:00 - 17:00")
        val adapterJam = ArrayAdapter(this, R.layout.dropdown_item, jamOptions)
        jamDropdown.setAdapter(adapterJam)

        jamDropdown.setOnItemClickListener { _, _, position, _ ->
            val selectedOption = adapterJam.getItem(position).toString()
            // Do something with the selected option
        }
    }

    private fun setupTanggal() {
        selectDateEditText.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun setupButtonBuatJanji() {
        buttonBuatJanji.setOnClickListener {
            buatJanji()
        }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)

                if (selectedDate >= Calendar.getInstance()) {
                    updateDateInView(selectedDate)
                } else {
                    Toast.makeText(this, "Please select a date in the future", Toast.LENGTH_SHORT).show()
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
        selectDateEditText.setText(sdf.format(calendar.time))
    }

    private fun buatJanji() {
        val selectedDate = selectDateEditText.text.toString().trim()
        val selectedJam = jamDropdown.text.toString().trim()

        if (selectedDate.isEmpty() || selectedJam.isEmpty()) {
            Toast.makeText(this, "Mohon isi semua data", Toast.LENGTH_SHORT).show()
            return
        }

        val emailPasien = "email_pasien_contoh@gmail.com"
        val emailDokter = "email_dokter_contoh@gmail.com"

        if (databaseHelper.saveAppointment(emailDokter, emailPasien, selectedDate, selectedJam)) {
            Toast.makeText(this, "Janji temu berhasil dibuat", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Gagal membuat janji temu", Toast.LENGTH_SHORT).show()
        }
    }
}
