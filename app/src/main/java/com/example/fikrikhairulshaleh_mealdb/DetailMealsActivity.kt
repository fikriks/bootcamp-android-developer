package com.example.fikrikhairulshaleh_mealdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.example.fikrikhairulshaleh_mealdb.data.database.MealEntity
import com.example.fikrikhairulshaleh_mealdb.data.network.handler.NetworkResult
import com.example.fikrikhairulshaleh_mealdb.databinding.ActivityDetailMealsBinding
import com.example.fikrikhairulshaleh_mealdb.model.MealsItem
import com.example.fikrikhairulshaleh_mealdb.model.ResponseMealDetail
import com.example.fikrikhairulshaleh_mealdb.viewmodels.DetailViewModel
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class DetailMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMealsBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private lateinit var mealDetail: ResponseMealDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val meal = intent.getParcelableExtra<MealsItem>(EXTRA_MEAL)

        supportActionBar?.apply {
            title = "Meal Detail"
            setDisplayHomeAsUpEnabled(true)
        }

        detailViewModel.fetchMealDetail(meal?.idMeal!!.toInt())

        detailViewModel.mealDetail.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> handleUi(
                    layoutWrapper = false,
                    progressBar = true,
                    errorTv = false,
                    errorImg = false
                )
                is NetworkResult.Error -> {
                    handleUi(
                        layoutWrapper = false,
                        progressBar = false,
                        errorTv = true,
                        errorImg = true
                    )
                    Toast.makeText(this, result.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Success -> {
                    mealDetail = result.data!!
                    binding.mealDetail = result.data!!

                    isFavoriteMeal(mealDetail)

                    handleUi(
                        layoutWrapper = true,
                        progressBar = false,
                        errorTv = false,
                        errorImg = false
                    )
                }
            }
        }
    }

    private fun isFavoriteMeal(mealSelected: ResponseMealDetail) {
        detailViewModel.favoriteMealList.observe(this) { result ->
            val meal = result.find { favorite ->
                favorite.meal.idMeal == mealSelected.meals?.get(0)?.idMeal
            }
            if (meal != null) {
                binding.addFavoriteBtn.apply {
                    setText(R.string.remove_from_favorite)
                    setBackgroundColor(
                        ContextCompat.getColor(
                            this@DetailMealsActivity,
                            R.color.red_star
                        )
                    )
                    setOnClickListener {
                        deleteFavoriteGame(meal.id)
                    }
                }
            } else {
                binding.addFavoriteBtn.apply {
                    setText(R.string.add_to_favorite)
                    setBackgroundColor(
                        ContextCompat.getColor(
                            this@DetailMealsActivity,
                            R.color.light_blue
                        )
                    )
                    setOnClickListener {
                        insertFavoriteGame()
                    }
                }
            }
        }
    }

    private fun deleteFavoriteGame(mealEntityId: Int) {
        val mealEntity = MealEntity(mealEntityId,mealDetail.meals?.get(0)!!)
        detailViewModel.deleteFavoriteMeal(mealEntity)
        MotionToast.createColorToast(this,
            "Hurray success 😍",
            "Successfully remove from favorite!",
            MotionToastStyle.SUCCESS,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular))
    }

    private fun insertFavoriteGame() {
        val mealEntity = MealEntity(meal = mealDetail.meals?.get(0)!!)
        detailViewModel.insertFavoriteMeal(mealEntity)
        MotionToast.createColorToast(this,
            "Hurray success 😍",
            "Successfully added to favorite!",
            MotionToastStyle.SUCCESS,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular))
    }

    private fun handleUi(
        layoutWrapper: Boolean,
        progressBar: Boolean,
        errorTv: Boolean,
        errorImg: Boolean
    ) {
        binding.apply {
            mealDetailWrapper.isVisible = layoutWrapper
            progressbar.isVisible = progressBar
            errorIcon.isVisible = errorImg
            errorText.isVisible = errorTv
        }
    }

    companion object {
        const val EXTRA_MEAL = "meal"
    }
}