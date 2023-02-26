package com.example.fikrikhairulshaleh_mealdb.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.fikrikhairulshaleh_mealdb.data.LocalDataSource
import com.example.fikrikhairulshaleh_mealdb.data.RemoteDataSource
import com.example.fikrikhairulshaleh_mealdb.data.Repository
import com.example.fikrikhairulshaleh_mealdb.data.database.MealDatabase
import com.example.fikrikhairulshaleh_mealdb.data.database.MealEntity
import com.example.fikrikhairulshaleh_mealdb.data.network.Service
import com.example.fikrikhairulshaleh_mealdb.data.network.handler.NetworkResult
import com.example.fikrikhairulshaleh_mealdb.model.ResponseMealDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    // API
    private val mealService = Service.MealService
    private val remote = RemoteDataSource(mealService)

    // LOCAL
    private val mealDao = MealDatabase.getDatabase(application).mealDao()
    private val local = LocalDataSource(mealDao)

    private val repository = Repository(remote,local)

    private var _mealDetail: MutableLiveData<NetworkResult<ResponseMealDetail>> = MutableLiveData()
    val mealDetail: LiveData<NetworkResult<ResponseMealDetail>> = _mealDetail

    fun fetchMealDetail(idMeal: Int) {
        viewModelScope.launch {
            repository.remote!!.getMealDetailById(idMeal).collect { result ->
                _mealDetail.value = result
            }
        }
    }

    val favoriteMealList: LiveData<List<MealEntity>> = repository.local!!.listMeal().asLiveData()
    fun insertFavoriteMeal(mealEntity: MealEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local!!.insertMeal(mealEntity)
        }
    }

    fun deleteFavoriteMeal(mealEntity: MealEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local!!.deleteMeal(mealEntity)
        }
    }
}