package com.example.codoc

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class AdapterChat  (private val listDokter:List<DokterModel>): RecyclerView.Adapter<AdapterChat.ViewHolder>() {
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView){
        val image: ImageView = itemView.findViewById(R.id.imageDokter)
        val nama: TextView = itemView.findViewById(R.id.textNama)
        val spesialis: TextView = itemView.findViewById(R.id.Spesialis)
        val kerja: TextView = itemView.findViewById(R.id.textkerja)
        val like: TextView = itemView.findViewById(R.id.textsuka)
        val harga: TextView = itemView.findViewById(R.id.textharga)
        val butt: Button = itemView.findViewById(R.id.button)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterChat.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.card_layout,parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: AdapterChat.ViewHolder, position: Int) {
        val modelDokter = listDokter[position]

        holder.image.setImageResource(modelDokter.image)
        holder.nama.text = modelDokter.nama
        holder.spesialis.text = modelDokter.spesialis
        holder.kerja.text = modelDokter.kerja
        holder.like.text = modelDokter.like
        holder.harga.text = modelDokter.harga
        holder.butt.text = "Chat"
        holder.butt.setOnClickListener {
            // Membuat Intent untuk membuka activity baru
            val intent = Intent(holder.itemView.context, ChatDokterActivity::class.java)
            // Menambahkan data DokterModel ke Intent
            intent.putExtra("DokterModel", modelDokter)
            // Memulai activity baru
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listDokter.size
    }
}