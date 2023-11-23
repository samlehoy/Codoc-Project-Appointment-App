package com.example.codoc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //reycle
        val rvDokter: RecyclerView = findViewById(R.id.recyclerViewDoctor)
        //set layout
        rvDokter.layoutManager = LinearLayoutManager(this)
        //list data buku
        val data = ArrayList<DokterModel>()
        data.add(DokterModel(R.drawable.icon1,"Dr. Ahmad", "Dokter Umum", "6 Tahun", "100%", "Rp 50.000"))
        data.add(DokterModel(R.drawable.icon1,"Dr. Udin", "Spesialis Mata", "10 Tahun", "98%", "Rp 50.000"))
        data.add(DokterModel(R.drawable.icon1,"Dr. Siti", "Spesialis Kulit", "11 Tahun", "99%", "Rp 50.000"))
        data.add(DokterModel(R.drawable.icon1,"Dr. Suli", "Spesialis kandungan", "14 Tahun", "97%", "Rp 50.000"))
        //set adpater
        val adapter = AdapterChat(data)
        //set adapter
        rvDokter.adapter = adapter
    }
}