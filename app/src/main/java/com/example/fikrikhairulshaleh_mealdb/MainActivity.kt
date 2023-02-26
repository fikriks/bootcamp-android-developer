package com.example.fikrikhairulshaleh_mealdb

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fikrikhairulshaleh_mealdb.adapter.MealsAdapter
import com.example.fikrikhairulshaleh_mealdb.data.network.handler.NetworkResult
import com.example.fikrikhairulshaleh_mealdb.databinding.ActivityMainBinding
import com.example.fikrikhairulshaleh_mealdb.model.MealsItem
import com.example.fikrikhairulshaleh_mealdb.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private val mealsAdapter by lazy { MealsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.listMeals.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    handleUi(
                        recyclerView = false,
                        progressBar = true,
                        errorTv = false,
                        errorImg = false
                    )
                }
                is NetworkResult.Error -> {
                    binding.errorText.text = result.errorMessage
                    handleUi(
                        recyclerView = false,
                        progressBar = false,
                        errorTv = true,
                        errorImg = true
                    )
                    Toast.makeText(this,result.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Success -> {
                    binding.rvBestMeals.apply {
                        adapter = mealsAdapter
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(
                            this@MainActivity,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        mealsAdapter.setData(result.data?.meals)
                    }
                    mealsAdapter.setOnItemClickCallback(object : MealsAdapter.IOnItemCallBack {
                        override fun onItemClickCallback(data: MealsItem) {
                            val intent = Intent(this@MainActivity,DetailMealsActivity::class.java)
                            intent.putExtra(DetailMealsActivity.EXTRA_MEAL,data)
                            startActivity(intent)
                        }

                    })
                    handleUi(
                        recyclerView = true,
                        progressBar = false,
                        errorTv = false,
                        errorImg = false
                    )
                }
            }
        }

        configTheme()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false)
        menu.findItem(R.id.app_bar_switch).isChecked = isDarkModeOn

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        return when (item.itemId) {
            R.id.app_bar_switch -> {
                if (item.isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    editor.putBoolean("isDarkModeOn", false)
                    editor.apply()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                    true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    editor.putBoolean("isDarkModeOn", true)
                    editor.apply()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                    true
                }
            }
            R.id.action_bookmark -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun configTheme(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false)

        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun handleUi(
        recyclerView: Boolean,
        progressBar: Boolean,
        errorTv: Boolean,
        errorImg: Boolean
    ) {
        binding.apply {
            rvBestMeals.isVisible = recyclerView
            progressbar.isVisible = progressBar
            errorIcon.isVisible = errorImg
            errorText.isVisible = errorTv
        }
    }
}