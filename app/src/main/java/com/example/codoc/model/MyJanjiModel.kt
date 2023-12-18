package com.example.codoc.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyJanjiModel (
    var id_janji:String,
    var emailPasien: String,
    var emailDokter: String,
    var tanggalJanji: String,
    var jamJanji: String,
) : Parcelable
