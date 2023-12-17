package com.example.codoc.activity.pasien

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*
import android.widget.Toast
import com.example.codoc.R
import com.example.codoc.DatabaseHelper



class PasienBookingActivity : AppCompatActivity() {

    private lateinit var selectDateEditText: TextInputEditText
    private val calendar = Calendar.getInstance()
    private val databaseHelper = DatabaseHelper(this)
    private lateinit var jamDropdown: AutoCompleteTextView
    private lateinit var buttonBuatJanji: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        selectDateEditText = findViewById(R.id.select_date)
        jamDropdown = findViewById(R.id.jam_dropdown)
        buttonBuatJanji = findViewById(R.id.buttonbuatjanji)



    //DROPDOWN JAM
        val jamDropdown = findViewById<AutoCompleteTextView>(R.id.jam_dropdown)
        val jamOptions = arrayOf("09:00 - 11:00", "11:00 - 13:00", "13:00 - 15:00", "15:00 - 17:00") // Gantilah dengan opsi yang sesuai
        val adapterJam = ArrayAdapter(this, R.layout.dropdown_item, jamOptions)
        jamDropdown.setAdapter(adapterJam)
        // Opsional: Tambahkan listener untuk menangani pemilihan opsi
        jamDropdown.setOnItemClickListener { _, _, position, _ ->
            val selectedOption = adapterJam.getItem(position).toString()
            // Lakukan sesuatu dengan opsi yang dipilih
        }

    //DROPDOWN JAM
        //val penyakitDropdown = findViewById<AutoCompleteTextView>(R.id.penyakit_dropdown)
        val penyakitOptions = arrayOf("09:00 - 11:00", "11:00 - 13:00", "13:00 - 15:00", "15:00 - 17:00") // Gantilah dengan opsi yang sesuai
        val adapterPenyakit = ArrayAdapter(this, R.layout.dropdown_item, penyakitOptions)
       // penyakitDropdown.setAdapter(adapterPenyakit)
        // Opsional: Tambahkan listener untuk menangani pemilihan opsi
        //penyakitDropdown.setOnItemClickListener { _, _, position, _ ->
         //   val selectedOption = adapterPenyakit.getItem(position).toString()
            // Lakukan sesuatu dengan opsi yang dipilih
       // }

    //TANGGAL
        selectDateEditText = findViewById(R.id.select_date)
        // Set a click listener to show the DatePickerDialog when the EditText is clicked
        selectDateEditText.setOnClickListener {
            showDatePickerDialog()

        }
        buttonBuatJanji.setOnClickListener {
            buatJanji()
        }


    }

    //untuk calender
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

        // Show the DatePickerDialog
        datePickerDialog.show()
    }

    //untuk calender
    private fun updateDateInView(calendar: Calendar) {
        val myFormat = "dd/MM/yyyy" // specify your format here
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

        // Dapatkan email pasien dan dokter dari sesi atau dari tempat lain sesuai kebutuhan
        val emailPasien = "email_pasien_contoh@gmail.com"
        val emailDokter = "email_dokter_contoh@gmail.com"

        // Simpan data janji temu ke dalam database
        if (databaseHelper.saveAppointment(emailDokter, emailPasien, selectedDate, selectedJam)) {
            Toast.makeText(this, "Janji temu berhasil dibuat", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Gagal membuat janji temu", Toast.LENGTH_SHORT).show()
        }
    }
}