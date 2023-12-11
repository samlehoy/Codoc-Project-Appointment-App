package com.example.codoc.activity.dokter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.codoc.DatabaseHelper
import com.example.codoc.databinding.ActivityProfileDokterBinding

class ProfileDokterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileDokterBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDokterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        setupViews()
    }

    private fun setupViews() {
        binding.apply {
            namauser.text = name
            spesialisDokter.text = spesialis
            alamat.text = alamatRs
            emailDokter.text = email
            noHpDokter.text = nohp
        }
    }

    companion object {
        var name = "Tes nama"
        var spesialis = "Tes nama"
        var alamatRs = "Tes nama"
        var email = "Tes nama"
        var nohp = "Tes nama"
    }
}