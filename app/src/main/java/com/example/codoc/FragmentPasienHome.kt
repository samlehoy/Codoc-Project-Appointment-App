package com.example.codoc

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codoc.adapter.AdapterDokter
import com.example.codoc.databinding.FragmentPasienHomeBinding
import com.google.android.material.chip.Chip

class FragmentPasienHome : Fragment() {

    private lateinit var binding: FragmentPasienHomeBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPasienHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        dbHelper = DatabaseHelper(requireContext())

        // List of specialties
        val listOfSpecialties = listOf("Umum", "THT", "Kulit", "Gigi", "Penyakit Dalam", "Kandungan", "Saraf")

        // Set up RecyclerView with the original list of doctors
        val rvmenu: RecyclerView = view.findViewById(R.id.recyclerViewDoctor)
        rvmenu.layoutManager = LinearLayoutManager(activity)
        val adapterDokter = AdapterDokter(emptyList())
        rvmenu.adapter = adapterDokter

        // Search functionality
        val doctorDataEditText = binding.doctorData
        doctorDataEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                // Call the search method with the entered query
                val searchResults = dbHelper.searchDoctors(query)
                // Update the RecyclerView adapter with the search results
                adapterDokter.updateData(searchResults)
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })

        // Sort the list of doctors by specialties
        val chipContainer: LinearLayout = view.findViewById(R.id.chipContainer)

        // Dynamically add chips to the chipContainer with margins
        for (specialty in listOfSpecialties) {
            val chip = Chip(context)
            chip.text = specialty

            // Set margin programmatically
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 8, 8, 8) // You can adjust the margins as needed
            chip.layoutParams = params

            chip.setOnClickListener {
                // Fetch the list of doctors from the database based on the selected specialty
                val doctorsListFromDB = dbHelper.getDoctorsBySpecialty(specialty)
                // Update the RecyclerView adapter with the filtered list
                adapterDokter.updateData(doctorsListFromDB)
            }
            // Set other chip properties if needed
            chipContainer.addView(chip)
        }

        // Fetch data from the database and update the RecyclerView adapter
        val doctorsListFromDB = dbHelper.getAllDoctors()
        adapterDokter.updateData(doctorsListFromDB)

        // Retrieve data "hi $user"
        val textNama: TextView = view.findViewById(R.id.namapasien)
        textNama.text = name

        return view
    }

    companion object {
        // var to store "hi $name"
        var name = "Tes nama"
    }
}
