package com.cs.testfoodapp.screens.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cs.foodapplandofcoding.model.MealsByCategory
import com.cs.testfoodapp.databinding.PopularItemsBinding

class MostPopularMealAdapter : RecyclerView.Adapter<MostPopularMealAdapter.PopularMealViewHolder>() {


    class PopularMealViewHolder(val binding: PopularItemsBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<MealsByCategory>(){
        override fun areItemsTheSame(oldItem: MealsByCategory, newItem: MealsByCategory): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(
            oldItem: MealsByCategory,
            newItem: MealsByCategory
        ): Boolean {
           return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
       return PopularMealViewHolder(
           PopularItemsBinding.inflate(
               LayoutInflater.from(parent.context),parent,false
           )
       )
    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {

        val popularItem = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this)
                .load(popularItem.strMealThumb)
                .into(holder.binding.imgPopularMealItem)

           setOnClickListener {
               onItemClickListener?.let {
                   it(popularItem)
               }
            }

        }
    }

    private var onItemClickListener : ((MealsByCategory) -> Unit)? = null

    fun setOnItemClickListener(listener: (MealsByCategory) -> Unit){
        onItemClickListener = listener
    }

}