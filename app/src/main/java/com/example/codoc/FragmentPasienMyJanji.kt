package com.example.codoc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codoc.activity.pasien.ProfilePasienActivity
import com.example.codoc.adapter.AdapterJanji
import com.example.codoc.databinding.FragmentPasienMyjanjiBinding

class FragmentPasienMyJanji : Fragment() {

    private lateinit var binding: FragmentPasienMyjanjiBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPasienMyjanjiBinding.inflate(inflater, container, false)
        dbHelper = DatabaseHelper(requireContext())
        setupRecyclerView(binding.root)
        return binding.root
    }

    private fun setupRecyclerView(view: View) {
        val rvMenu: RecyclerView = view.findViewById(R.id.recyclerViewDoctor)
        rvMenu.layoutManager = LinearLayoutManager(activity)

        val myJanjiListFromDB = dbHelper.getDataJanjiOnPasien(ProfilePasienActivity.email)
        rvMenu.adapter = AdapterJanji(myJanjiListFromDB)
    }

    companion object {
    }
}
