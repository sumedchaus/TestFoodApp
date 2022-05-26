package com.cs.testfoodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cs.testfoodapp.databinding.ActivityMainBinding
import com.cs.testfoodapp.screens.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // for bottom navigation view
        val navController = findNavController(R.id.foodNavHostFragment)
        binding.bottomNavigationView.setupWithNavController(navController)




    }
}