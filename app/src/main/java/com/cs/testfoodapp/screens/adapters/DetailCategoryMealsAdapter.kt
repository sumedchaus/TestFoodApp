package com.cs.testfoodapp.screens.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cs.foodapplandofcoding.model.MealsByCategory
import com.cs.testfoodapp.databinding.MealItemBinding

class DetailCategoryMealsAdapter : RecyclerView.Adapter<DetailCategoryMealsAdapter.CategoryMealsViewModel>() {

    private var mealsList = ArrayList<MealsByCategory>()

    fun setMealsList(mealList: List<MealsByCategory>){
        this.mealsList = mealList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }

    inner class CategoryMealsViewModel(val binding: MealItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewModel {
       return CategoryMealsViewModel(
           MealItemBinding.inflate(
               LayoutInflater.from(parent.context)
           )
       )
    }

    override fun getItemCount(): Int {
        return  mealsList.size
    }

    override fun onBindViewHolder(holder: CategoryMealsViewModel, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = mealsList[position].strMeal

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(mealsList[position])
        }
    }
    var onItemClick : ((MealsByCategory) -> Unit)? = null





}