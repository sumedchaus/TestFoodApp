package com.cs.testfoodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cs.testfoodapp.communicator.Communicator
import com.cs.testfoodapp.databinding.ActivityMainBinding
import com.cs.testfoodapp.screens.fragments.HomeFragment
import com.cs.testfoodapp.screens.fragments.TestFragment
import com.cs.testfoodapp.screens.viewmodel.HomeViewModel
import com.cs.testfoodapp.utils.Constants
import com.cs.testfoodapp.utils.Constants.MEAL_ID
import com.cs.testfoodapp.utils.Constants.MEAL_NAME
import com.cs.testfoodapp.utils.Constants.MEAL_THUMB
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Communicator {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // for bottom navigation view
        val navController = findNavController(R.id.foodNavHostFragment)
        binding.bottomNavigationView.setupWithNavController(navController)

        // to set home fragment as main Fragment
//        val homeFragment = HomeFragment()
//        supportFragmentManager.beginTransaction().replace(R.id.home_fragment_main, homeFragment).commit()



    }

    /**
     * simplest way to pass data between activities
     *
     */

//    override fun passData(mealId:String,mealName:String,mealThumb:String) {
//        val bundle = Bundle()
//        bundle.putString(MEAL_ID, mealId)
//        bundle.putString(MEAL_NAME, mealName)
//        bundle.putString(MEAL_THUMB, mealThumb)
//
//        val transaction = this.supportFragmentManager.beginTransaction()
//        val testFragment = TestFragment()
//        testFragment.arguments = bundle
//
//        transaction.replace(R.id.home_fragment_main,testFragment)
//        transaction.commit()
//    }
}