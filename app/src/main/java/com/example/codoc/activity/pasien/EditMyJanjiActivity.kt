package com.example.codoc.activity.pasien

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.codoc.DatabaseHelper
import com.example.codoc.FragmentDokterMyJanji
import com.example.codoc.R
import com.example.codoc.model.MyJanjiModel
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class EditMyJanjiActivity : AppCompatActivity() {

    private lateinit var selectDateEditText: TextInputEditText
    private lateinit var jamDropdown: AutoCompleteTextView
    private lateinit var btnUpdate: Button
    private val calendar = Calendar.getInstance()
    private val databaseHelper = DatabaseHelper(this)

    companion object {
        var idJanji = 1
        var tanggalJanji = "test"
        var jamJanji = "test"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_my_janji)

        // Hide title bar
        supportActionBar?.hide()

        // Instance
        val textId: TextInputEditText = findViewById(R.id.idJanji)
        val namaDokter: TextInputEditText = findViewById(R.id.namaDokter)
        val emailPasien: TextInputEditText = findViewById(R.id.emailPasien)
        val emailDokter: TextInputEditText = findViewById(R.id.emailDokter)
        val textTanggal: TextInputEditText = findViewById(R.id.editTanggal)
        val textJam: AutoCompleteTextView = findViewById(R.id.editJam)
        val spesialisDokter:TextInputEditText = findViewById(R.id.spesialisDokter)
        btnUpdate = findViewById(R.id.buttonUpdate)

        // Initialize selectDateEditText
        selectDateEditText = findViewById(R.id.editTanggal)

        // Setup dropdown menu for time
        jamDropdown = findViewById(R.id.editJam)
        setupJamDropdown()

        // Setup date selection
        setupTanggal()

        // Retrieve ID_JANJI from intent
        val idJanji = intent.getStringExtra("ID_JANJI")
        val tanggalJanji = intent.getStringExtra("TANGGAL_JANJI")
        val jamJanji = intent.getStringExtra("JAM_JANJI")

        // Set data
        textId.setText(idJanji.toString())
        textTanggal.setText(tanggalJanji)

        // Set the dropdown selection
        val jamOptions = arrayOf("09:00 - 11:00", "11:00 - 13:00", "13:00 - 15:00", "15:00 - 17:00")
        val selectedJamPosition = jamOptions.indexOf(jamJanji)
        textJam.setText(jamOptions[selectedJamPosition])

        // Now, set the adapter for the dropdown
        setupJamDropdown()

        btnUpdate.setOnClickListener {
            // Object class DatabaseHelper
            val databaseHelper = DatabaseHelper(this)

            val idJanji: String = textId.text.toString().trim()
            val namaDokter: String = namaDokter.text.toString().trim()
            val emailPasien: String = emailPasien.text.toString().trim()
            val emailDokter: String = emailDokter.text.toString().trim()
            val tanggalJanji: String = textTanggal.text.toString().trim()
            val jamJanji: String = textJam.text.toString().trim()
            val spesialis:String = spesialisDokter.text.toString().trim()

            val janjiModel = MyJanjiModel(idJanji, namaDokter, emailPasien, emailDokter, tanggalJanji, jamJanji, spesialis)
            databaseHelper.updateJanji(janjiModel)

            // Intent main activity
            val intent = Intent(this, HomePasienActivity::class.java)
            startActivity(intent)
            finish()
        }
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
}