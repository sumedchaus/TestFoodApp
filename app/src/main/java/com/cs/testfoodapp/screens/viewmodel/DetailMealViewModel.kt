package com.cs.testfoodapp.screens.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cs.foodapplandofcoding.model.Meal
import com.cs.foodapplandofcoding.model.MealResponse
import com.cs.testfoodapp.networking.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMealViewModel: ViewModel() {
    private val _detailMealLiveData = MutableLiveData<Meal>()
    val detailMealLiveData : LiveData<Meal> = _detailMealLiveData

    fun getMealDetails(id: String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealResponse>{
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                if(response.isSuccessful){
                    _detailMealLiveData.value = response.body()!!.meals[0]
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Log.d("Error", t.message.toString())

            }

        })
    }
}