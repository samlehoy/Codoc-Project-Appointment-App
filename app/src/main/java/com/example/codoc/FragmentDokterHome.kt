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
import com.example.codoc.databinding.FragmentDokterHomeBinding
import com.example.codoc.model.DokterModel
import com.google.android.material.chip.Chip

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentDokterHome.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentDokterHome : Fragment() {

    private lateinit var binding: FragmentDokterHomeBinding
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
        binding = FragmentDokterHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        // Sample list of specialties
        val listOfSpecialties = listOf("Umum", "Cardiology", "Dermatology", "Orthopedics", "Neurology", "Dentist")

        // Original list of doctors
        val dokterList = listOf(
            DokterModel( "Dr. Ahmad", "Umum", "RS Medika", "09AM-05PM"),
            DokterModel( "Dr. Erna", "Cardiology", "RSCM", "09AM-05PM"),
            DokterModel( "Dr. Rachel", "Dermatology", "Tot", "09AM-05PM"),
            // Add more doctors as needed
        )

        // Set up RecyclerView with the original list of doctors
        val rvmenu: RecyclerView = view.findViewById(R.id.recyclerViewDoctor)
        rvmenu.layoutManager = LinearLayoutManager(activity)
        var adapterDokter = AdapterDokter(dokterList)
        rvmenu.adapter = adapterDokter

        // Sort the list of doctors by specialties
        val chipContainer: LinearLayout = view.findViewById(R.id.chipContainer)

        // Dynamically add chips to the chipContainer
        for (specialty in listOfSpecialties) {
            val chip = Chip(context)
            chip.text = specialty
            chip.setOnClickListener {
                // Filter the list based on the selected specialty
                val filteredDokterList = dokterList.filter { it.spesialis == specialty }
                // Update the RecyclerView adapter with the filtered list
                adapterDokter.updateData(filteredDokterList)
            }
            // Set other chip properties if needed
            chipContainer.addView(chip)
        }

        dbHelper = DatabaseHelper(requireContext())

        //to retrieve data on ProfileActivity
        sharedPreferences = requireContext().getSharedPreferences("user_prefs", AppCompatActivity.MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("user_email", "")
        Log.d("ProfileActivity", "User Email from SharedPreferences: $userEmail")
        if (userEmail.isNullOrBlank()) {
            // Handle the case where user email is not available (user not logged in)
            Log.e("ProfileActivity", "User email not found.")
        } else {
            fetchDoctorName(userEmail)
        }
        return view
    }

    // Implement this function to fetch the doctor's name based on the user email
    private fun fetchDoctorName(userEmail: String?): Unit {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            DatabaseHelper.COLUMN_NAME_DOKTER,
            DatabaseHelper.COLUMN_EMAIL_DOKTER
        )

        val selection = "${DatabaseHelper.COLUMN_EMAIL_DOKTER} = ?"
        val selectionArgs = arrayOf(userEmail)

        val cursor = db.query(
            DatabaseHelper.TABLE_AKUNDOKTER,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        cursor.use {
            if (it.moveToFirst()) {
                val name = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME_DOKTER))
                binding.namadokter.text = "$name"
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
            FragmentDokterHome().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}