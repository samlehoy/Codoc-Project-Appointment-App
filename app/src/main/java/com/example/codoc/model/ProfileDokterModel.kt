package com.example.codoc.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileDokterModel (
    var nama:String,
    var spesialis:String,
    var alamat:String,
    var email:String,
    var noHp:String,
) : Parcelable {

}