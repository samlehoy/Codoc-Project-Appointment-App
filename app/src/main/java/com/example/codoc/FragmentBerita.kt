package com.example.codoc

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codoc.adapter.AdapterDokter
import com.example.codoc.databinding.FragmentDokterHomeBinding
import com.example.codoc.model.DokterCardModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentBerita.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentBerita : Fragment() {
    private lateinit var binding: FragmentBerita
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_berita, container, false)


        // Set up RecyclerView with the original list of doctors
        val rvmenu: RecyclerView = view.findViewById(R.id.recyclerViewDoctor)
        rvmenu.layoutManager = LinearLayoutManager(activity)
        var adapterberita = AdapterDokter(dokterList)
        rvmenu.adapter = adapterDokter

        // Original list of doctors
        val dokterList = listOf(
            DokterCardModel( "Dr. Ahmad", "Umum", "RS Medika", "09AM-05PM"),
            DokterCardModel( "Dr. Erna", "Cardiology", "RSCM", "09AM-05PM"),
            DokterCardModel( "Dr. Rachel", "Dermatology", "Tot", "09AM-05PM"),
            // Add more doctors as needed
        )
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentRumahSakit.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentBerita().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}