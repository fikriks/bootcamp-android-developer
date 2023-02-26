package com.example.fikrikhairulshaleh_mealdb.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fikrikhairulshaleh_mealdb.data.LocalDataSource
import com.example.fikrikhairulshaleh_mealdb.data.RemoteDataSource
import com.example.fikrikhairulshaleh_mealdb.data.Repository
import com.example.fikrikhairulshaleh_mealdb.data.database.MealDatabase
import com.example.fikrikhairulshaleh_mealdb.data.database.MealEntity
import com.example.fikrikhairulshaleh_mealdb.data.network.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteDetailMealViewModel(application: Application): AndroidViewModel(application) {
    // API
    private val gameService = Service.MealService
    private val remote = RemoteDataSource(gameService)

    // LOCAL
    private val gameDao = MealDatabase.getDatabase(application).mealDao()
    private val local = LocalDataSource(gameDao)
    private val repository = Repository(remote,local)

    fun deleteFavoriteMeal(gameEntity: MealEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local!!.deleteMeal(gameEntity)
        }
    }
}