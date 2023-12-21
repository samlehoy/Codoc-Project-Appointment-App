package com.example.codoc.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.codoc.DatabaseHelper
import com.example.codoc.R
import com.example.codoc.model.MyJanjiModel

class AdapterJanjiOnDokter(private var listDokter: List<MyJanjiModel>) :
    RecyclerView.Adapter<AdapterJanjiOnDokter.ViewHolder>() {

    fun updateData(newList: List<MyJanjiModel>) {
        listDokter = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nama: TextView = itemView.findViewById(R.id.textNama)
        private val spesialis: TextView = itemView.findViewById(R.id.Spesialis)
        private val jam: TextView = itemView.findViewById(R.id.jamtext)
        private val tanggal: TextView = itemView.findViewById(R.id.tanggaltext)
        private val idJanji: TextView = itemView.findViewById(R.id.idJanji)

        fun bind(modelJanji: MyJanjiModel) {
            nama.text = modelJanji.namaDokter
            spesialis.text = modelJanji.spesialis
            jam.text = modelJanji.jamJanji
            tanggal.text = modelJanji.tanggalJanji
            idJanji.text = modelJanji.id_janji

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.janji_card_layout_ondokter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listDokter[position])
    }

    override fun getItemCount(): Int {
        return listDokter.size
    }
}