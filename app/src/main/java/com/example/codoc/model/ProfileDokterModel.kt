package com.example.codoc.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileDokterModel (
    var email:String,
    var nama:String,
    var spesialis:String,
    var rumahSakit:String,
    var jadwal:String,
) : Parcelable {

}