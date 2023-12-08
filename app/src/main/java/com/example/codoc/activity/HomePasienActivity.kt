package com.example.codoc.activity

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.codoc.FragmentHome
import com.example.codoc.FragmentMyJanji
import com.example.codoc.FragmentSettings
import com.example.codoc.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePasienActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_pasien)

        //hide title bar
        getSupportActionBar()?.hide()

        //instance
        val bottomNav:BottomNavigationView = findViewById(R.id.bottomNav);

        //set fragment
        val homeFragment= FragmentHome()
        val settingsFragment= FragmentSettings()
        val myJanjiFragment= FragmentMyJanji()

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


