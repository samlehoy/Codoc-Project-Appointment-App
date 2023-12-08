package com.example.codoc.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.codoc.R

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        //instance
        val btnMasukpasien: ImageView = findViewById(R.id.pasienimage)
        val btnMasukdokter: ImageView = findViewById(R.id.dokterimage)

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