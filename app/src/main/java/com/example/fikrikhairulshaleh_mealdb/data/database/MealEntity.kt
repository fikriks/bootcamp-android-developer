package com.example.fikrikhairulshaleh_mealdb.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fikrikhairulshaleh_mealdb.model.MealItemDetail
import com.example.fikrikhairulshaleh_mealdb.utils.Constant.MEAL_TABLE_NAME
import kotlinx.parcelize.Parcelize

@Entity(tableName = MEAL_TABLE_NAME)
@Parcelize
data class MealEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val meal: MealItemDetail
):Parcelable