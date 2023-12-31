package com.example.codoc.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.codoc.R
import com.example.codoc.activity.dokter.LoginDokterActivity
import com.example.codoc.activity.pasien.LoginPasienActivity

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        //instance
        val btnMasukpasien: ImageView = findViewById(R.id.pasienImage)
        val btnMasukdokter: ImageView = findViewById(R.id.dokterImage)

        //action button
        btnMasukpasien.setOnClickListener {
            val intentpasien = Intent (this, LoginPasienActivity::class.java)
            startActivity(intentpasien)
        }
        btnMasukdokter.setOnClickListener {
            val intentdokter = Intent (this, LoginDokterActivity::class.java)
            startActivity(intentdokter)
        }
    }
}