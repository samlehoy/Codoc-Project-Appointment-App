package com.example.codoc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentHome.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentHome : Fragment() {
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Sample list of specialties
        val listOfSpecialties = listOf("Umum", "Cardiology", "Dermatology", "Orthopedics", "Neurology", "Dentist")

        // Original list of doctors
        val dokterList = listOf(
            DokterModel(R.drawable.cowok, "Dr. Ahmad", "Umum", "5 Years", "1200"),
            DokterModel(R.drawable.cewek, "Dr. Erna", "Cardiology", "10 Years", "500"),
            DokterModel(R.drawable.cewek, "Dr. Rachel", "Dermatology", "7 Years", "800"),
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

        return view
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
            FragmentHome().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}