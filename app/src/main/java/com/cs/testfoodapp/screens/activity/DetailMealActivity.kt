package com.cs.testfoodapp.screens.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.cs.testfoodapp.R
import com.cs.testfoodapp.databinding.ActivityMealDetailBinding
import com.cs.testfoodapp.screens.viewmodel.DetailMealViewModel
import com.cs.testfoodapp.utils.Constants.MEAL_ID
import com.cs.testfoodapp.utils.Constants.MEAL_NAME
import com.cs.testfoodapp.utils.Constants.MEAL_THUMB

class DetailMealActivity : AppCompatActivity() {

    private val viewModel: DetailMealViewModel by viewModels()
//    lateinit var viewModel : DetailMealViewModel

    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var youtubeLink: String


    private lateinit var binding: ActivityMealDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        viewModel = ViewModelProvider(this)[DetailMealViewModel::class.java]


        getMealInformationFromIntent()
        setIInformationInViews()
        viewModel.getMealDetails(mealId)
        getMealInformationFromViewModel()
        onYoutubeImageClick()
    }


    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(MEAL_ID)!!
        mealName = intent.getStringExtra(MEAL_NAME)!!
        mealThumb = intent.getStringExtra(MEAL_THUMB)!!
    }

    private fun setIInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolBar.title = mealName
        binding.collapsingToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolBar.setExpandedTitleColor(resources.getColor(R.color.white))

    }

    private fun getMealInformationFromViewModel() {

        viewModel.detailMealLiveData.observe(this, Observer { meal ->

            binding.tvCategory.text = "Category: ${meal.strCategory} "
            binding.tvArea.text = "Area: ${meal.strArea} "
            binding.instructionDetails.text = "Area: ${meal.strInstructions} "
            youtubeLink = meal.strYoutube!!
        })
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }


}