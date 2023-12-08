package com.example.codoc.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.codoc.activity.DetailDokterActivity
import com.example.codoc.R
import com.example.codoc.model.DokterModel

class AdapterDokter(var listDokter: List<DokterModel>) : RecyclerView.Adapter<AdapterDokter.ViewHolder>() {
    // Existing code...

    // Add this method to update the data in the adapter
    fun updateData(newList: List<DokterModel>) {
        listDokter = newList
        notifyDataSetChanged()
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView){
        val image: ImageView = itemView.findViewById(R.id.imageDokter)
        val nama: TextView = itemView.findViewById(R.id.textNama)
        val spesialis: TextView = itemView.findViewById(R.id.Spesialis)
        val kerja: TextView = itemView.findViewById(R.id.textkerja)
        val like: TextView = itemView.findViewById(R.id.textsuka)
        val butt: Button = itemView.findViewById(R.id.book_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.doctor_card_layout,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modelDokter = listDokter[position]

        holder.image.setImageResource(modelDokter.image)
        holder.nama.text = modelDokter.nama
        holder.spesialis.text = modelDokter.spesialis
        holder.kerja.text = modelDokter.kerja
        holder.like.text = modelDokter.like
        holder.butt.text = "Book"
        holder.butt.setOnClickListener {
            // Membuat Intent untuk membuka activity baru
            val intent = Intent(holder.itemView.context, DetailDokterActivity::class.java)
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