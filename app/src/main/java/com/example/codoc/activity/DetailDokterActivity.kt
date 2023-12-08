package com.example.codoc.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.codoc.R
import com.example.codoc.model.DokterModel

class DetailDokterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_dokter)

        val backButton: ImageView = findViewById(R.id.back)
        backButton.setOnClickListener {
            val intent = Intent (this, HomeActivity::class.java)
            startActivity(intent)
        }

        val book_appointment: AppCompatButton = findViewById(R.id.book_appointment)
        book_appointment.setOnClickListener {
            val intent = Intent (this, BookingActivity::class.java)
            startActivity(intent)
        }


        // Mengambil data yang dikirim dari activity sebelumnya
        val dokterId = intent.getIntExtra("dokterId", 0)

        val user = intent.getParcelableExtra<DokterModel>("DokterModel")
        //semua
        val nams = findViewById<TextView>(R.id.namadokter)
        val spes = findViewById<TextView>(R.id.spesialis)
        nams.text = user?.nama
        spes.text = user?.spesialis

    }
}