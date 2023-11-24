package com.example.codoc

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class DetailDokterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_dokter)


        val backButton: ImageView = findViewById(R.id.back)
        backButton.setOnClickListener {
            val intent = Intent (this, HomeActivity::class.java)
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