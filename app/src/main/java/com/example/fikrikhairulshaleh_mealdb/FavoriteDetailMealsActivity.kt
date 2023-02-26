package com.example.fikrikhairulshaleh_mealdb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.fikrikhairulshaleh_mealdb.data.database.MealEntity
import com.example.fikrikhairulshaleh_mealdb.databinding.ActivityFavoriteDetailMealsBinding
import com.example.fikrikhairulshaleh_mealdb.viewmodels.FavoriteDetailMealViewModel

class FavoriteDetailMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteDetailMealsBinding
    private val favoriteDetailViewModel by viewModels<FavoriteDetailMealViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteDetailMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Favorite Meal Detail"
            setDisplayHomeAsUpEnabled(true)
        }

        val favoriteMeal = intent.getParcelableExtra<MealEntity>(EXTRA_FAVORITE_MEAL)
        binding.mealDetail = favoriteMeal!!.meal

        binding.removeFavoriteBtn.setOnClickListener {
            deleteFavoriteMeal(favoriteMeal)
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun deleteFavoriteMeal(mealEntity: MealEntity) {
        favoriteDetailViewModel.deleteFavoriteMeal(mealEntity)
        Toast.makeText(this, "Successfully remove from favorite", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_FAVORITE_MEAL = "favorite_meal"
    }
}