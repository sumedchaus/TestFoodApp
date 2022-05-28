package com.cs.testfoodapp.screens.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.mvvmnewsapp.util.Resource
import com.bumptech.glide.Glide
import com.cs.foodapplandofcoding.model.Meal
import com.cs.testfoodapp.databinding.FragmentHomeBinding
import com.cs.testfoodapp.screens.activity.DetailCategoryMealsActivity
import com.cs.testfoodapp.screens.activity.DetailMealActivity
import com.cs.testfoodapp.screens.adapters.CategoriesAdapter
import com.cs.testfoodapp.screens.adapters.MostPopularMealAdapter
import com.cs.testfoodapp.screens.viewmodel.HomeViewModel
import com.cs.testfoodapp.utils.Constants.CATEGORY_NAME
import com.cs.testfoodapp.utils.Constants.MEAL_ID
import com.cs.testfoodapp.utils.Constants.MEAL_NAME
import com.cs.testfoodapp.utils.Constants.MEAL_THUMB
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    private val TAG = "HomeFragment"

    private lateinit var popularItemAdapter: MostPopularMealAdapter
    private lateinit var categoryListAdapter: CategoriesAdapter

    lateinit var randomMeal: Meal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popularItemAdapter = MostPopularMealAdapter()
        categoryListAdapter = CategoriesAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                viewModel.getRandomMealList()
                mainHandler.postDelayed(this, 5000)
            }
        })

        // get randomMeals
        getRandomMeals()
        onRandomMealClick()
        //get popular items
        viewModel.getPopularItems()
        getPopularItemsList()
        preparePopularItemsRecycleView()
        popularItemClickListener()
        //category List
        viewModel.getCategories()
        getCategoriesList()
        categoryListItemsRecyclerView()
        onCategoryClick()


    }


    private fun getRandomMeals() {
        viewModel.randomMealList.observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is Resource.Success -> {
                    hideProgressBar()

                    this.randomMeal = response.data!!.meals[0]
                    Glide.with(this)
                        .load(response.data.meals[0].strMealThumb)
                        .into(binding.imgRandomMeal)
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

    }

    private fun onRandomMealClick(){
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, DetailMealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }


    //get popular Items View
    private fun getPopularItemsList() {
        //get popular Items
        viewModel.popularItemsLiveData.observe(viewLifecycleOwner, Observer { popularMeals ->
            popularItemAdapter.differ.submitList(popularMeals)
        })
    }

    // get popular Items recycler view
    private fun preparePopularItemsRecycleView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

            adapter = popularItemAdapter
        }
    }
    //click event on popular items
    private fun popularItemClickListener() {
        popularItemAdapter.setOnItemClickListener { meal ->
            val intent = Intent(activity, DetailMealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)

        }
    }


    //get category list
    private fun getCategoriesList() {
        viewModel.categoryListLiveData.observe(viewLifecycleOwner, Observer { categoryList ->
            categoryListAdapter.differ.submitList(categoryList)

        })
    }
    // create category list recycler view

    private fun categoryListItemsRecyclerView() {
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(activity, 3)

            adapter = categoryListAdapter
        }
    }

    private fun onCategoryClick(){
        categoryListAdapter.onItemClick = { category ->
        val intent = Intent(activity, DetailCategoryMealsActivity::class.java)
        intent.putExtra(CATEGORY_NAME, category.strCategory)
        startActivity(intent)

        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }


}