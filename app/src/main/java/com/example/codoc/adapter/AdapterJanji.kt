package com.example.codoc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.codoc.R
import com.example.codoc.model.MyJanjiModel
class AdapterJanji(var listDokter: List<MyJanjiModel>) : RecyclerView.Adapter<AdapterJanji.ViewHolder>() {

    fun updateData(newList: List<MyJanjiModel>) {
        listDokter = newList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nama: TextView = itemView.findViewById(R.id.textNama)
        val spesialis: TextView = itemView.findViewById(R.id.Spesialis)
        val jam: TextView = itemView.findViewById(R.id.jamtext)
        val tanggal: TextView = itemView.findViewById(R.id.tanggaltext)
        val idJanji: TextView = itemView.findViewById(R.id.idJanji)
        val editButton: ImageButton = itemView.findViewById(R.id.imageButton2)
        val deleteButton: ImageButton = itemView.findViewById(R.id.imageButton3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.janji_card_layout, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modelDokter = listDokter[position]

        holder.nama.text = modelDokter.emailDokter
        holder.spesialis.text = modelDokter.tanggalJanji
        holder.jam.text = modelDokter.jamJanji
        holder.tanggal.text = modelDokter.tanggalJanji
        holder.idJanji.text = modelDokter.id_janji

        holder.editButton.setOnClickListener {
            // Handle edit button click
        }

        holder.deleteButton.setOnClickListener {
            // Handle delete button click
        }
    }

    override fun getItemCount(): Int {
        return listDokter.size
    }
}