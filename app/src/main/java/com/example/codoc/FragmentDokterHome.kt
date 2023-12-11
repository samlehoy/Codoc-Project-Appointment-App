package com.example.codoc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.codoc.databinding.FragmentDokterHomeBinding

class FragmentDokterHome : Fragment() {

    private lateinit var binding: FragmentDokterHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDokterHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        // Retrieve data "hi $user"
        val textNama: TextView = view.findViewById(R.id.namadokter)
        textNama.text = name

        return view
    }

    companion object {
        var name = "Tes nama"
    }
}
