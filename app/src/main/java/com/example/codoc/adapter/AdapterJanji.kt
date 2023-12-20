package com.example.codoc.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.codoc.DatabaseHelper
import com.example.codoc.R
import com.example.codoc.activity.pasien.EditMyJanjiActivity
import com.example.codoc.activity.pasien.HomePasienActivity
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
        val editButton: ImageButton = itemView.findViewById(R.id.buttonEdit)
        val deleteButton: ImageButton = itemView.findViewById(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.janji_card_layout, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modelDokter = listDokter[position]

        holder.nama.text = modelDokter.namaDokter
        holder.spesialis.text = modelDokter.tanggalJanji
        holder.jam.text = modelDokter.jamJanji
        holder.tanggal.text = modelDokter.tanggalJanji
        holder.idJanji.text = modelDokter.id_janji

        holder.editButton.setOnClickListener {
            // Handle edit button click
        }

        holder.deleteButton.setOnClickListener {
            // Log statement to check if the click event is triggered
            Log.d("AdapterJanji", "Delete button clicked for ID: ${modelDokter.id_janji}")

            // Handle delete button click
            val dbHelper = DatabaseHelper(holder.itemView.context)
            dbHelper.deleteJanji(modelDokter.id_janji)

            // Log statement to check if deleteJanji function is called
            Log.d("AdapterJanji", "Deleting Janji with ID: ${modelDokter.id_janji}")

            // Notify the adapter that the data set has changed
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return listDokter.size
    }

    inner class MakananViewHolder(v: View):RecyclerView.ViewHolder(v) {
        val textId: TextView
        val buttonEdit: Button
        val buttonDelete: Button
        val context = v.context


        init {
            textId = v.findViewById(R.id.idJanji)
            buttonEdit = v.findViewById(R.id.buttonEdit)
            buttonDelete = v.findViewById(R.id.buttonDelete)

            //event saat buttonedit di tekan
            buttonEdit.setOnClickListener {
                //set data sesuai data yang dipilih

                val intent = Intent(context, EditMyJanjiActivity::class.java)
                context.startActivity(intent)
            }

            //event saat buttonHapus di tekan
            buttonDelete.setOnClickListener {
                val dbHelper = DatabaseHelper(context)
                dbHelper.deleteJanji(textId.text.toString())
                val intent = Intent(context, HomePasienActivity::class.java)
                context.startActivity(intent)
            }
        }
    }
}
