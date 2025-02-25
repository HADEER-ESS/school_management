package com.hadeer.schoolapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.hadeer.domain.utils.SharedPrefs
import com.hadeer.schoolapp.R
import com.hadeer.schoolapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleToolBarTitle()
    }

    private fun handleToolBarTitle() {
        val userName = SharedPrefs.getStringData(this, "user_name")
        binding.topBarInclude.topAppBar.title = "Welcome $userName,"

        //setup the Toolbar
        setSupportActionBar(binding.topBarInclude.topAppBar)
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.navigation_fragment_container
        ) as? NavHostFragment

        val navController = navHostFragment?.navController

    }

    private fun showPadgeForAnnouncement(){
        binding.bottomAppBarNav.getOrCreateBadge(R.id.announcementFragment).number = 5
    }

}