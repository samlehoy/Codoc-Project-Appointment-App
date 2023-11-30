package com.example.codoc

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*
import android.widget.Toast


class BookingActivity : AppCompatActivity() {

    private lateinit var selectDateEditText: TextInputEditText
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        selectDateEditText = findViewById(R.id.select_date)

        // Set a click listener to show the DatePickerDialog when the EditText is clicked
        selectDateEditText.setOnClickListener {
            showDatePickerDialog()
        }
    }

    //
    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                // Update the TextInputEditText with the selected date
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)

                if (selectedDate >= Calendar.getInstance()) {
                    // Update the TextInputEditText with the selected current or future date
                    updateDateInView(selectedDate)
                } else {
                    // Show a message if the selected date is in the past
                    // This can be customized based on your requirements
                    // For example, you might want to show a message and not update the date
                    // Or you can show a Toast message
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

    private fun updateDateInView(calendar: Calendar) {
        val myFormat = "dd/MM/yyyy" // specify your format here
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        selectDateEditText.setText(sdf.format(calendar.time))
    }
}