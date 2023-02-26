package com.example.fikrikhairulshaleh_mealdb.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.fikrikhairulshaleh_mealdb.data.LocalDataSource
import com.example.fikrikhairulshaleh_mealdb.data.Repository
import com.example.fikrikhairulshaleh_mealdb.data.database.MealDatabase
import com.example.fikrikhairulshaleh_mealdb.data.database.MealEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    // LOCAL
    private val mealDao = MealDatabase.getDatabase(application).mealDao()
    private val local = LocalDataSource(mealDao)

    private val repository = Repository(local = local)

    val favoriteGameList: LiveData<List<MealEntity>> = repository.local!!.listMeal().asLiveData()

    fun deleteFavoritesMeal(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local!!.deleteAllMeal()
        }
    }
}