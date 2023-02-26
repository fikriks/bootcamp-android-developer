package com.example.fikrikhairulshaleh_mealdb.data.database

import androidx.room.*
import com.example.fikrikhairulshaleh_mealdb.utils.Constant.MEAL_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(mealEntity: MealEntity)

    @Query("SELECT * FROM $MEAL_TABLE_NAME ORDER BY id ASC")
    fun liistMeal(): Flow<List<MealEntity>>

    @Delete()
    suspend fun deleteMeal(mealEntity: MealEntity)

    @Query("DELETE FROM $MEAL_TABLE_NAME")
    suspend fun deleteAllMeal()
}