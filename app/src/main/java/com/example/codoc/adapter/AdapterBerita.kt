package com.example.codoc.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.codoc.activity.dokter.DetailDokterActivity
import com.example.codoc.R
import com.example.codoc.model.BeritaModel
import com.example.codoc.model.DokterCardModel

class AdapterBerita(var listberita: List<BeritaModel>) : RecyclerView.Adapter<AdapterBerita.ViewHolder>() {

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView){
        val image: ImageView = itemView.findViewById(R.id.fotoberita)
        val judul: TextView = itemView.findViewById(R.id.judulberita)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.berita_card_layout,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val beritaModel = listberita[position]


        holder.judul.text = beritaModel.judul

    }


    override fun getItemCount(): Int {
        return listberita.size
    }

}