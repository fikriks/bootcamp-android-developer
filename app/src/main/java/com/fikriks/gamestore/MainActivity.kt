package com.fikriks.gamestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fikriks.gamestore.adapter.ListThumbnailAdapter
import com.fikriks.gamestore.data.Thumbnail
import com.fikriks.gamestore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val list = ArrayList<Thumbnail>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvThumbnails.setHasFixedSize(true)

        list.addAll(getListThumbnails())
        showRecyclerList()
    }

    private fun getListThumbnails(): ArrayList<Thumbnail> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listThumbnail = ArrayList<Thumbnail>()

        for (i in dataName.indices) {
            val thumbnail = Thumbnail(dataPhoto.getResourceId(i, -1))
            listThumbnail.add(thumbnail)
        }

        return listThumbnail
    }

    private fun showRecyclerList() {
        binding.rvThumbnails.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listHeroAdapter = ListThumbnailAdapter(list)
        binding.rvThumbnails.adapter = listHeroAdapter
    }
}