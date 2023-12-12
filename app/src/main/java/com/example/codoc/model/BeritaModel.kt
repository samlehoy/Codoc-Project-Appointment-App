package com.example.codoc.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BeritaModel (
    var image:Int,
    var judul:String
) : Parcelable {

}