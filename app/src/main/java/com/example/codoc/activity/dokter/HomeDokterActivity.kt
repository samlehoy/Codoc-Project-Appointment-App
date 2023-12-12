package com.example.codoc.activity.dokter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.codoc.FragmentBerita
import com.example.codoc.FragmentDokterHome
import com.example.codoc.FragmentDokterMyJanji
import com.example.codoc.FragmentDokterSettings
import com.example.codoc.FragmentPasienMyJanji
import com.example.codoc.FragmentPasienSettings
import com.example.codoc.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeDokterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_dokter)

        //hide title bar
        getSupportActionBar()?.hide()

        //instance
        val bottomNav: BottomNavigationView = findViewById(R.id.bottomNav);

        //set fragment
        val homeFragment= FragmentDokterHome()
        val settingsFragment= FragmentDokterSettings()
        val myJanjiFragment= FragmentDokterMyJanji()
        val beritaFragment= FragmentBerita()

        //default fragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container,homeFragment)
            commit()
        }
        currentFragment(homeFragment)

        //fungsi pindah fragment melalu button nav
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    currentFragment(homeFragment)
                    true
                }
                R.id.appointment -> {
                    currentFragment(myJanjiFragment)
                    true
                }
                R.id.settings -> {
                    currentFragment(settingsFragment)
                    true
                }
                R.id.stats ->{
                    currentFragment(beritaFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun currentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container,fragment)
            commit()
        }


}
