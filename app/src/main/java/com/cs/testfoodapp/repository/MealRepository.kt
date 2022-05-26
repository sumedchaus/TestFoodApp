package com.cs.testfoodapp.repository

import com.cs.foodapplandofcoding.model.MealResponse
import com.cs.testfoodapp.networking.MealApi
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Response
import javax.inject.Inject

@ActivityScoped
class MealRepository  @Inject constructor(private val api:MealApi) {

    //function provides List of Meal from api
    suspend fun getRandomMealsList(): Response<MealResponse> {
        return  api.getRandomMeal()
    }


}