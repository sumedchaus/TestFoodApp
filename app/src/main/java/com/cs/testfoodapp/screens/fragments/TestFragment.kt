package com.cs.testfoodapp.screens.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.cs.testfoodapp.R
import com.cs.testfoodapp.databinding.FragmentHomeBinding
import com.cs.testfoodapp.databinding.FragmentTestBinding
import com.cs.testfoodapp.utils.Constants
import com.cs.testfoodapp.utils.Constants.MEAL_ID
import com.cs.testfoodapp.utils.Constants.MEAL_NAME
import com.cs.testfoodapp.utils.Constants.MEAL_THUMB


class TestFragment : Fragment() {
    val  arguments : TestFragmentArgs by navArgs()
    lateinit var binding:FragmentTestBinding
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTestBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

//        val data = arguments
//        mealId = data!!.getString(MEAL_ID).toString()
//        mealName = data.get(MEAL_NAME).toString()
//        mealThumb = data.getString(MEAL_THUMB).toString()
//        binding.instructionDetails.text = mealName


        mealName = arguments.mealName
        mealId = arguments.mealId
        mealThumb = arguments.mealThumb

        binding.collapsingToolBar.title = mealName
        binding.collapsingToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolBar.setExpandedTitleColor(resources.getColor(R.color.white))

        Glide.with(activity!!)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }




}