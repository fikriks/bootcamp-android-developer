package com.example.fikrikhairulshaleh_mealdb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fikrikhairulshaleh_mealdb.adapter.FavoriteAdapter
import com.example.fikrikhairulshaleh_mealdb.data.database.MealEntity
import com.example.fikrikhairulshaleh_mealdb.databinding.ActivityFavoriteBinding
import com.example.fikrikhairulshaleh_mealdb.viewmodels.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel>()
    private val favoriteMealAdapter by lazy { FavoriteAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Favorite Meals"
            setDisplayHomeAsUpEnabled(true)
        }

        favoriteViewModel.favoriteGameList.observe(this) { result ->
            if (result.isEmpty()) {
                binding.apply {
                    rvFavoriteMeal.isVisible = false
                    emptyTv.isVisible = true
                    emptyIcon.isVisible = true
                }
            } else {
                binding.rvFavoriteMeal.apply {
                    adapter = favoriteMealAdapter
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(
                        this@FavoriteActivity
                    )
                }

                favoriteMealAdapter.apply {
                    setData(result)
                    setOnItemClickCallback(object:FavoriteAdapter.IOnFavoriteItemCallBack{
                        override fun onFavoriteItemClickCallback(data: MealEntity) {
                            val intent = Intent(this@FavoriteActivity,FavoriteDetailMealsActivity::class.java)
                            intent.putExtra(FavoriteDetailMealsActivity.EXTRA_FAVORITE_MEAL,data)
                            startActivity(intent)
                        }
                    })
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_favorite, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_all -> {
                val alertDialog = AlertDialog.Builder(this)

                alertDialog.apply {
                    setIcon(R.drawable.ic_warning)
                    setTitle("Warning")
                    setMessage("Are you sure to delete all favorites meal?")
                    setPositiveButton("Yes, Sure") { _, _ ->
                        deleteFavoritesMeal()
                    }
                    setNegativeButton("No") { _, _ ->
                        false
                    }
                }.create().show()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteFavoritesMeal() {
        favoriteViewModel.deleteFavoritesMeal()
        Toast.makeText(this, "Successfully remove all meal from favorites", Toast.LENGTH_SHORT).show()
    }
}