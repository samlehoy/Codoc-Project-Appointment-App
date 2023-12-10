package com.example.codoc

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.google.android.material.chip.Chip

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentPasienHome : Fragment() {

    private lateinit var binding: FragmentPasienHomeBinding
    private lateinit var dbHelper: DatabaseHelper
    //to retrieve data on ProfileActivity
    private lateinit var sharedPreferences: SharedPreferences

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

        //FUNGSI SEARCH
        val doctorDataEditText = binding.doctorData
        doctorDataEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }
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