package com.example.codoc

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codoc.adapter.AdapterDokter
import com.example.codoc.databinding.FragmentPasienHomeBinding
import com.example.codoc.model.DokterCardModel
import com.google.android.material.chip.Chip

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentPasienHome.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentPasienHome : Fragment() {

    private lateinit var binding: FragmentPasienHomeBinding
    private lateinit var dbHelper: DatabaseHelper
    //to retrieve data on ProfileActivity
    private lateinit var sharedPreferences: SharedPreferences

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPasienHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        dbHelper = DatabaseHelper(requireContext())  // Initialize dbHelper first

        // Sample list of specialties
        val listOfSpecialties = listOf("Umum", "THT", "Kulit", "Gigi", "Penyakit Dalam", "Kandungan", "Saraf")

        // Set up RecyclerView with the original list of doctors
        val rvmenu: RecyclerView = view.findViewById(R.id.recyclerViewDoctor)
        rvmenu.layoutManager = LinearLayoutManager(activity)
        var adapterDokter = AdapterDokter(emptyList())  // Initialize with an empty list
        rvmenu.adapter = adapterDokter

        // Sort the list of doctors by specialties
        val chipContainer: LinearLayout = view.findViewById(R.id.chipContainer)

        // Dynamically add chips to the chipContainer
        for (specialty in listOfSpecialties) {
            val chip = Chip(context)
            chip.text = specialty
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

        // ...

        return view
    }

    // Implement this function to fetch the doctor's name based on the user email
    private fun fetchDoctorName(userEmail: String?): Unit {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            DatabaseHelper.COLUMN_NAME_PASIEN,
            DatabaseHelper.COLUMN_EMAIL_PASIEN
        )

        val selection = "${DatabaseHelper.COLUMN_EMAIL_PASIEN} = ?"
        val selectionArgs = arrayOf(userEmail)

        val cursor = db.query(
            DatabaseHelper.TABLE_AKUNPASIEN,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        cursor.use {
            if (it.moveToFirst()) {
                val name = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME_PASIEN))
                binding.namapasien.text = "$name"
            } else {
                Log.e("FragmentHome", "No data found for user with email: $userEmail")
            }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentHome.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentPasienHome().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}