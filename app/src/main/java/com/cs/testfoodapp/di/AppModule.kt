package com.cs.testfoodapp.di

import com.cs.testfoodapp.utils.Constants.BASE_URL
import com.cs.testfoodapp.networking.MealApi
import com.cs.testfoodapp.repository.MealRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Provides
    @Singleton
    fun provideMealRepository(
        api: MealApi
    ) = MealRepository(api)

    /** create an instance of retrofit */
    @Singleton
    @Provides
    fun provideMealApi(): MealApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(MealApi::class.java)
    }
}