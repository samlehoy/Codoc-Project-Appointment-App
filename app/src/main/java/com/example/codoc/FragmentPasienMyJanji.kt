package com.example.codoc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codoc.activity.pasien.ProfilePasienActivity
import com.example.codoc.adapter.AdapterJanji
import com.example.codoc.databinding.FragmentPasienMyjanjiBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentPasienMyJanji.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentPasienMyJanji : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentPasienMyjanjiBinding
    private lateinit var dbHelper: DatabaseHelper

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
        binding = FragmentPasienMyjanjiBinding.inflate(inflater, container, false)
        val view = binding.root
        dbHelper = DatabaseHelper(requireContext())

        // Set up RecyclerView with the original list of doctors
        val rvmenu: RecyclerView = view.findViewById(R.id.recyclerViewDoctor)
        rvmenu.layoutManager = LinearLayoutManager(activity)

        // Fetch data from the database and update the RecyclerView adapter
        val myjanjiListFromDB = dbHelper.getDataJanjiPasien(ProfilePasienActivity.email)
        Toast.makeText(this.requireContext(),myjanjiListFromDB.toString(), Toast.LENGTH_SHORT).show()
        rvmenu.adapter = AdapterJanji(myjanjiListFromDB)
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentPasienMyJanji().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}