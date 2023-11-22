package com.example.codoc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UmumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_umum)

        //button back
        val btnback : ImageView = findViewById(R.id.back)

        btnback.setOnClickListener {
            val intent = Intent (this,ChatActivity::class.java)
            startActivity(intent)
        }


        //reycle
        val rvDokter: RecyclerView = findViewById(R.id.recyclerViewBuku)
        //set layout
        rvDokter.layoutManager = LinearLayoutManager(this)
        //list data buku
        val data = ArrayList<DokterModel>()
        data.add(DokterModel(R.drawable.dokter,"Dr. Ahmad", "Dokter Umum", "6 Tahun", "100%", "Rp 50.000"))
        data.add(DokterModel(R.drawable.dokter,"Dr. Udin", "Spesialis Mata", "10 Tahun", "98%", "Rp 50.000"))
        data.add(DokterModel(R.drawable.dokter,"Dr. Siti", "Spesialis Kulit", "11 Tahun", "99%", "Rp 50.000"))
        data.add(DokterModel(R.drawable.dokter,"Dr. Suli", "Spesialis kandungan", "14 Tahun", "97%", "Rp 50.000"))
        //set adpater
        val adapter = AdapterChat(data)
        //set adapter
        rvDokter.adapter = adapter

    }
}