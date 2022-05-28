package com.cs.testfoodapp.screens.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cs.testfoodapp.R
import com.cs.testfoodapp.databinding.ActivityDetailCategoryMealsBinding
import com.cs.testfoodapp.screens.adapters.CategoriesAdapter
import com.cs.testfoodapp.screens.adapters.DetailCategoryMealsAdapter
import com.cs.testfoodapp.screens.fragments.HomeFragment
import com.cs.testfoodapp.screens.viewmodel.DetailCategoryViewModel
import com.cs.testfoodapp.screens.viewmodel.DetailMealViewModel
import com.cs.testfoodapp.utils.Constants.CATEGORY_NAME
import com.cs.testfoodapp.utils.Constants.MEAL_ID
import com.cs.testfoodapp.utils.Constants.MEAL_NAME
import com.cs.testfoodapp.utils.Constants.MEAL_THUMB

class DetailCategoryMealsActivity : AppCompatActivity() {

    private val viewModel: DetailCategoryViewModel by viewModels()
    lateinit var mealCategory: String
    lateinit var categoryMealAdapter: DetailCategoryMealsAdapter

    lateinit var binding: ActivityDetailCategoryMealsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getDataFromIntent()
        viewModel.getSeparateMealsByCategory(mealCategory)
        // or u can use it directly like this
//      viewModel.getSeparateMealsByCategory(intent.getStringExtra(CATEGORY_NAME)!!)
        prepareRecyclerView()
        getSeparateMealsByCategory()
        detailCategoryListClick()



    }

    private fun getDataFromIntent() {
        mealCategory = intent.getStringExtra(CATEGORY_NAME)!!
    }

    private fun prepareRecyclerView(){
        categoryMealAdapter = DetailCategoryMealsAdapter()
        binding.rvMeal.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealAdapter
        }
    }

    private fun getSeparateMealsByCategory() {
        viewModel.separateItemsCategoryLiveData.observe(this, Observer{ mealsList ->

            binding.tvCategoryMealsAppBar.text = mealCategory
            binding.tvCategoryCount.text = "Total Items: ${mealsList.size}"
            categoryMealAdapter.setMealsList(mealsList)

        })
    }

    private fun detailCategoryListClick(){
        categoryMealAdapter.onItemClick = { meal ->
            val intent = Intent(this, DetailMealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }


}