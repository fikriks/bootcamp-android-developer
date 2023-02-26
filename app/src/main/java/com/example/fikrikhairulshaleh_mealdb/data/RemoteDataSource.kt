package com.example.fikrikhairulshaleh_mealdb.data

import android.util.Log
import com.example.fikrikhairulshaleh_mealdb.data.network.api.MealApi
import com.example.fikrikhairulshaleh_mealdb.data.network.handler.NetworkResult
import com.example.fikrikhairulshaleh_mealdb.model.ResponseMealDetail
import com.example.fikrikhairulshaleh_mealdb.model.ResponseMeals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val mealApi: MealApi) {
    suspend fun getMealsList(queries: Map<String, String>): Flow<NetworkResult<ResponseMeals>> = flow {
        try {
            emit(NetworkResult.Loading(true))
            val meals = mealApi.getMealsList(queries)

            // request data successful
            if (meals.isSuccessful) {
                val responseBody = meals.body()
                // if data empty
                if (responseBody?.meals?.isEmpty() == true) {
                    emit(NetworkResult.Error("List game not found."))
                } else {
                    emit(NetworkResult.Success(responseBody!!))
                }
            } else {
                // request data failed
                Log.d("apiServiceError", "statusCode:${meals.code()}, message:${meals.message()}")
                emit(NetworkResult.Error("Failed to fetch data from server."))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("remoteError", "${e.message}")
            emit(NetworkResult.Error("Something went wrong. Please check log."))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getMealDetailById(idMeal: Int): Flow<NetworkResult<ResponseMealDetail>> = flow {
        try {
            emit(NetworkResult.Loading(true))
            val gameDetail = mealApi.getMealDetailById(idMeal)
            if (gameDetail.isSuccessful) {
                val responseBody = gameDetail.body()

                if (responseBody != null) {
                    emit(NetworkResult.Success(responseBody))
                } else {
                    emit(NetworkResult.Error("Can't fetch detail game."))
                }
            } else {
                // request data failed
                Log.d(
                    "apiServiceError",
                    "statusCode:${gameDetail.code()}, message:${gameDetail.message()}"
                )
                emit(NetworkResult.Error("Failed to fetch data from server."))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("remoteError", "${e.message}")
            emit(NetworkResult.Error("Something went wrong. Please check log."))
        }
    }.flowOn(Dispatchers.IO)
}