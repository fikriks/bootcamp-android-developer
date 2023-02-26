package com.example.fikrikhairulshaleh_mealdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fikrikhairulshaleh_mealdb.databinding.ItemRowMealsBinding
import com.example.fikrikhairulshaleh_mealdb.model.MealsItem

class MealsAdapter : RecyclerView.Adapter<MealsAdapter.MealViewHolder>() {
    private val diffCallBack = object : DiffUtil.ItemCallback<MealsItem>() {

        override fun areItemsTheSame(oldItem: MealsItem, newItem: MealsItem): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealsItem, newItem: MealsItem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)
    private lateinit var onItemCallBack: IOnItemCallBack

    inner class MealViewHolder(private val binding: ItemRowMealsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MealsItem) {
            binding.apply {
                data = item
                itemView.setOnClickListener {
                    onItemCallBack.onItemClickCallback(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            ItemRowMealsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val itemData = differ.currentList[position]
        holder.bind(itemData)
    }

    fun setData(list: List<MealsItem?>?) {
        differ.submitList(list)
    }

    fun setOnItemClickCallback(action: IOnItemCallBack) {
        this.onItemCallBack = action
    }

    interface IOnItemCallBack {
        fun onItemClickCallback(data: MealsItem)
    }
}