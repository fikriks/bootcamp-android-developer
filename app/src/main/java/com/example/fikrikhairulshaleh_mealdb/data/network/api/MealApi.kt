package com.example.fikrikhairulshaleh_mealdb.data.network.api

import com.example.fikrikhairulshaleh_mealdb.model.ResponseMealDetail
import com.example.fikrikhairulshaleh_mealdb.model.ResponseMeals
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MealApi {
    @GET("filter.php?c=Seafood")
    suspend fun getMealsList(
        @QueryMap queries: Map<String, String>
    ): Response<ResponseMeals>

    @GET("lookup.php?")
    suspend fun getMealDetailById(
        @Query("i") idMeal:Int
    ): Response<ResponseMealDetail>

}