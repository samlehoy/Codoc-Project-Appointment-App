package com.example.codoc.activity.pasien

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.codoc.DatabaseHelper
import com.example.codoc.FragmentDokterMyJanji
import com.example.codoc.R
import com.example.codoc.model.MyJanjiModel
import com.google.android.material.textfield.TextInputEditText

class EditMyJanjiActivity : AppCompatActivity() {
    companion object{
        var idJanji = 1
        var tanggalJanji = "test"
        var jamJanji = "test"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_my_janji)

        //hide title bar
        getSupportActionBar()?.hide()

        //instance
        val textId : TextInputEditText = findViewById(R.id.idJanji)
        val namaDokter : TextInputEditText = findViewById(R.id.namaDokter)
        val emailPasien : TextInputEditText = findViewById(R.id.emailPasien)
        val emailDokter : TextInputEditText = findViewById(R.id.emailDokter)
        val textTanggal : EditText = findViewById(R.id.editTanggal)
        val textJam : TextInputEditText = findViewById(R.id.editJam)
        val btnUpdate : Button = findViewById(R.id.buttonUpdate)

        //set data
        textId.setText(idJanji.toString())
        textTanggal.setText(tanggalJanji)
        textJam.setText(jamJanji.toString())

        btnUpdate.setOnClickListener{
            //object class databaseHelper
            val databaseHelper = DatabaseHelper(this)

            val id_janji : String = textId.text.toString().trim()
            val namaDokter : String = namaDokter.text.toString().trim()
            val emailPasien : String = emailPasien.text.toString().trim()
            val emailDokter : String = emailDokter.text.toString().trim()
            val tanggalJanji : String = textTanggal.text.toString().trim()
            val jamJanji : String = textJam.text.toString().trim()

            val janjiModel = MyJanjiModel(id_janji, namaDokter, emailPasien, emailDokter, tanggalJanji, jamJanji)
            databaseHelper.updateJanji(janjiModel)

            //intent main activity
            val intent = Intent(this,FragmentDokterMyJanji::class.java)
            startActivity(intent)

            finish()
        }


    }

}