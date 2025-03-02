package com.hadeer.schoolapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.hadeer.domain.utils.SharedPrefs
import com.hadeer.schoolapp.R
import com.hadeer.schoolapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        binding.bottomAppBarNav.setupWithNavController(navController!!)

        binding.topBarInclude.topBarBackImg.setOnClickListener {
            navController.navigateUp()
        }

        navController.addOnDestinationChangedListener{_,destination,_ ->
            when(destination.id){
                R.id.homeMainFragment -> {
                    binding.topBarInclude.toolbarTitle.text =
                        getString(R.string.welcome_txt, userName)
                    binding.topBarInclude.topBarNotificationImg.visibility = View.VISIBLE
                    binding.topBarInclude.topBarBackImg.visibility = View.GONE
                }
                else ->{
                    binding.topBarInclude.topBarNotificationImg.visibility = View.GONE
                    binding.topBarInclude.topBarBackImg.visibility = View.VISIBLE
                    binding.topBarInclude.toolbarTitle.text = destination.label
                }
            }
        }
    }

    private fun showPadgeForAnnouncement(){
        binding.bottomAppBarNav.getOrCreateBadge(R.id.announcementFragment).number = 5
    }

}