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
import com.example.codoc.activity.pasien.EditMyJanjiActivity
import com.example.codoc.activity.pasien.HomePasienActivity
import com.example.codoc.model.MyJanjiModel

class AdapterJanji(private var listDokter: List<MyJanjiModel>) :
    RecyclerView.Adapter<AdapterJanji.ViewHolder>() {

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
        private val editButton: ImageButton = itemView.findViewById(R.id.buttonEdit)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.buttonDelete)

        fun bind(modelDokter: MyJanjiModel) {
            nama.text = modelDokter.namaDokter
            spesialis.text = modelDokter.tanggalJanji
            jam.text = modelDokter.jamJanji
            tanggal.text = modelDokter.tanggalJanji
            idJanji.text = modelDokter.id_janji

            editButton.setOnClickListener {
                handleEditButtonClick(modelDokter.id_janji, itemView.context)
            }

            deleteButton.setOnClickListener {
                handleDeleteButtonClick(modelDokter.id_janji, itemView.context)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.janji_card_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listDokter[position])
    }

    override fun getItemCount(): Int {
        return listDokter.size
    }

    private fun handleEditButtonClick(id: String, context: Context) {
        Log.d("AdapterJanji", "Edit button clicked for ID: $id")
        val intent = Intent(context, EditMyJanjiActivity::class.java)
        intent.putExtra("ID_JANJI", id)
        context.startActivity(intent)
    }

    private fun handleDeleteButtonClick(id: String, context: Context) {
        Log.d("AdapterJanji", "Delete button clicked for ID: $id")
        val dbHelper = DatabaseHelper(context)
        dbHelper.deleteJanji(id)
        Log.d("AdapterJanji", "Deleting Janji with ID: $id")
        notifyDataSetChanged()
    }
}
