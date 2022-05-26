package com.cs.testfoodapp.screens.fragments

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
import com.cs.testfoodapp.databinding.FragmentHomeBinding
import com.cs.testfoodapp.screens.adapters.CategoriesAdapter
import com.cs.testfoodapp.screens.adapters.MostPopularMealAdapter
import com.cs.testfoodapp.screens.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    private val TAG = "HomeFragment"

    private lateinit var popularItemAdapter: MostPopularMealAdapter
    private lateinit var categoryListAdapter: CategoriesAdapter

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
        //get popular items
        viewModel.getPopularItems()
        preparePopularItemsRecycleView()
        //category List
        viewModel.getCategories()
        categoryListItemsRecyclerView()

        viewModel.randomMealList.observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is Resource.Success -> {
                    hideProgressBar()

                    Glide.with(this)
                        .load(response.data!!.meals[0].strMealThumb)
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

        //get popular Items
        viewModel.popularItemsLiveData.observe(viewLifecycleOwner, Observer { popularMeals ->
            popularItemAdapter.differ.submitList(popularMeals)
        })

        viewModel.categoryListLiveData.observe(viewLifecycleOwner, Observer { categoryList ->
            categoryListAdapter.differ.submitList(categoryList)

        })
    }

    // get popular Items recycler view
    private fun preparePopularItemsRecycleView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

            adapter = popularItemAdapter
        }
    }
    private fun categoryListItemsRecyclerView() {
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(activity,3)

            adapter = categoryListAdapter
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }


}