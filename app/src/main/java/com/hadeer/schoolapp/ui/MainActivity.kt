package com.hadeer.schoolapp.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.hadeer.domain.utils.SharedPrefs
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
    }


}