package com.example.codoc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //hide title bar
        getSupportActionBar()?.hide()

        //instance
        val bottomNav:BottomNavigationView = findViewById(R.id.bottomNav);

        //set fragment
        val homeFragment=FragmentHome()
        val settingsFragment=FragmentSettings()
        val myJanjiFragment=FragmentMyJanji()

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


