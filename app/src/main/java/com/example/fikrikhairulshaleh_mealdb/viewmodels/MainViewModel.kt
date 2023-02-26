package com.example.fikrikhairulshaleh_mealdb.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fikrikhairulshaleh_mealdb.data.RemoteDataSource
import com.example.fikrikhairulshaleh_mealdb.data.Repository
import com.example.fikrikhairulshaleh_mealdb.data.network.Service
import com.example.fikrikhairulshaleh_mealdb.data.network.handler.NetworkResult
import com.example.fikrikhairulshaleh_mealdb.model.ResponseMeals
import com.example.fikrikhairulshaleh_mealdb.utils.Constant.QUERY_DATES
import com.example.fikrikhairulshaleh_mealdb.utils.Constant.QUERY_ORDERING
import com.example.fikrikhairulshaleh_mealdb.utils.Constant.QUERY_PAGE_SIZE
import kotlinx.coroutines.launch

class MainViewModel  : ViewModel() {
    // API
    private val mealService = Service.MealService
    private val remote = RemoteDataSource(mealService)

    private val repository = Repository(remote)

    private var _listMeals: MutableLiveData<NetworkResult<ResponseMeals>> = MutableLiveData()
    val listMeals: LiveData<NetworkResult<ResponseMeals>> = _listMeals

    init {
        fetchListMeals()
    }

    private fun fetchListMeals() {
        viewModelScope.launch {
            repository.remote?.getMealsList(applyQueries())?.collect { result ->
                _listMeals.value = result
            }
        }
    }

    private fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_PAGE_SIZE] = "20"
        queries[QUERY_ORDERING] = "-added"
        queries[QUERY_DATES] = "2023-01-01,2023-12-31"

        return queries
    }
}