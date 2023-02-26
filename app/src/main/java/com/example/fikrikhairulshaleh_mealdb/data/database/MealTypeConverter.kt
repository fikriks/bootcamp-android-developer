package com.example.fikrikhairulshaleh_mealdb.data.database

import androidx.room.TypeConverter
import com.example.fikrikhairulshaleh_mealdb.model.MealItemDetail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MealTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun mealDataToString(game: MealItemDetail): String {
        return gson.toJson(game)
    }

    @TypeConverter
    fun mealStringToData(string: String): MealItemDetail {
        val listType = object : TypeToken<MealItemDetail>() {}.type
        return gson.fromJson(string, listType)
    }
}