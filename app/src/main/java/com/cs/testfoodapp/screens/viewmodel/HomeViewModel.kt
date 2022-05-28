package com.cs.testfoodapp.screens.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.util.Resource
import com.cs.foodapplandofcoding.model.*
import com.cs.testfoodapp.di.AppModules
import com.cs.testfoodapp.networking.MealApi
import com.cs.testfoodapp.networking.RetrofitInstance
import com.cs.testfoodapp.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val mealRepository: MealRepository) : ViewModel() {

    private val _randomMealList = MutableLiveData<Resource<MealResponse>>()
    val randomMealList: LiveData<Resource<MealResponse>> = _randomMealList

    private var _popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    val popularItemsLiveData: LiveData<List<MealsByCategory>> = _popularItemsLiveData

    private var _categoryListLiveData = MutableLiveData<List<Category>>()
    val categoryListLiveData: LiveData<List<Category>> = _categoryListLiveData




    fun getRandomMealList() {
        viewModelScope.launch {

            _randomMealList.postValue(Resource.Loading())
            val response = mealRepository.getRandomMealsList()
            _randomMealList.postValue(responseToResource(response))
            Log.d("TAG", "getRandomMealList: ${response.body()}")
        }
    }

    // to convert response to resource
    // because we are using resource class for success failure and loading state
    private fun responseToResource(response: Response<MealResponse>): Resource<MealResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun getPopularItems() {
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitInstance.api.getPopularItems("Seafood")
                .enqueue(object : Callback<MealsByCategoryList> {
                    override fun onResponse(
                        call: Call<MealsByCategoryList>,
                        response: Response<MealsByCategoryList>
                    ) {
                        if (response.isSuccessful) {
                            _popularItemsLiveData.value = response.body()!!.meals
                            val result = _popularItemsLiveData.value
                            Log.d("Test", "onResponse: $result")
                        }
                    }

                    override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                        Log.d("Error Home Fragment", t.message.toString())
                    }
                })
        }
    }

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitInstance.api.getCategory().enqueue(object : Callback<CategoryList> {
                override fun onResponse(
                    call: Call<CategoryList>,
                    response: Response<CategoryList>
                ) {
                    if (response.isSuccessful) {
                        _categoryListLiveData.value = response.body()!!.categories
                    }
                }

                override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                    Log.d("Error Home Fragment", t.message.toString())

                }
            })
        }

    }



}
