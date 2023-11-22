package com.example.codoc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ChatDokterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_dokter)


        // Mengambil data yang dikirim dari activity sebelumnya
        val dokterId = intent.getIntExtra("dokterId", 0)

        val user = intent.getParcelableExtra<DokterModel>("DokterModel")
        //semua
        val nams = findViewById<TextView>(R.id.namadokter)
        nams.text = user?.nama
    }
}