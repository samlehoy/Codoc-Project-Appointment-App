package com.example.codoc.activity.dokter

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.codoc.R
import com.example.codoc.activity.pasien.PasienBookingActivity
import com.example.codoc.activity.pasien.HomePasienActivity
import com.example.codoc.model.ProfileDokterModel

class DetailDokterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_dokter)

        val backButton: ImageView = findViewById(R.id.back)
        backButton.setOnClickListener {
            val intent = Intent(this, HomePasienActivity::class.java)
            startActivity(intent)
        }

        val book_appointment: AppCompatButton = findViewById(R.id.book_appointment)
        book_appointment.setOnClickListener {
            val intent = Intent(this, PasienBookingActivity::class.java)
            startActivity(intent)
        }

        // Mengambil data yang dikirim dari activity sebelumnya
        val dokterId = intent.getIntExtra("dokterId", 0)

        val user = intent.getParcelableExtra<ProfileDokterModel>("DokterModel")
        //semua
        val nams = findViewById<TextView>(R.id.namadokter)
        val spes = findViewById<TextView>(R.id.spesialis)
        val alamat = findViewById<TextView>(R.id.doctor_address)
        nams.text = user?.nama
        spes.text = user?.spesialis
        alamat.text = user?.alamat
        Toast.makeText(this, user?.nama.toString() + " - " + user?.spesialis, Toast.LENGTH_SHORT).show()

        // Menambahkan OnClickListener untuk email
        val emailIdView = findViewById<ImageView>(R.id.email_id)
        emailIdView.setOnClickListener {
            sendEmail(user?.email)
        }

        // Menambahkan OnClickListener untuk nomor telepon
        val phoneCallView = findViewById<ImageView>(R.id.phone_call)
        phoneCallView.setOnClickListener {
            dialPhoneNumber(user?.noHp)
        }
    }

    // Fungsi untuk mengirim email
    private fun sendEmail(email: String?) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:$email")
        startActivity(intent)
    }

    // Fungsi untuk melakukan panggilan telepon
    private fun dialPhoneNumber(phoneNumber: String?) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    }
}