package com.cs.testfoodapp.screens.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.cs.testfoodapp.R
import com.cs.testfoodapp.databinding.FragmentCategoriesBinding
import com.cs.testfoodapp.databinding.FragmentHomeBinding
import com.cs.testfoodapp.screens.activity.DetailCategoryMealsActivity
import com.cs.testfoodapp.screens.adapters.CategoriesAdapter
import com.cs.testfoodapp.screens.viewmodel.HomeViewModel
import com.cs.testfoodapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var categoryListAdapter: CategoriesAdapter


    private lateinit var binding: FragmentCategoriesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoryListAdapter = CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getCategories()
        getCategoriesList()
        categoryListItemsRecyclerView()
        onCategoryClick()
    }

    private fun getCategoriesList() {
        viewModel.categoryListLiveData.observe(viewLifecycleOwner, Observer { categoryList ->
            categoryListAdapter.differ.submitList(categoryList)

        })
    }

    private fun categoryListItemsRecyclerView() {
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(activity, 3)

            adapter = categoryListAdapter
        }
    }
    private fun onCategoryClick(){
        categoryListAdapter.onItemClick = { category ->
            val intent = Intent(activity, DetailCategoryMealsActivity::class.java)
            intent.putExtra(Constants.CATEGORY_NAME, category.strCategory)
            startActivity(intent)

        }
    }

}