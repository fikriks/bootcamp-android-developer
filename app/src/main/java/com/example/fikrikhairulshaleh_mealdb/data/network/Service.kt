package com.example.fikrikhairulshaleh_mealdb.data.network

import com.example.fikrikhairulshaleh_mealdb.data.network.api.MealApi
import com.example.fikrikhairulshaleh_mealdb.data.network.config.ApiInterceptor
import com.example.fikrikhairulshaleh_mealdb.utils.Constant.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Service {
    private val client = OkHttpClient
        .Builder()
        .addInterceptor(ApiInterceptor())
        .writeTimeout(20, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val MealService: MealApi by lazy {
        retrofit.create(MealApi::class.java)
    }
}