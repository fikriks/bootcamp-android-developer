package com.bootcamp.tugas3_bootcampidn.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.tugas3_bootcampidn.*
import com.bootcamp.tugas3_bootcampidn.databinding.ItemRowNewsBinding
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter() :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var dataNews: List<ArticlesItem> = listOf()

    inner class NewsViewHolder(private val binding: ItemRowNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        private val formatter = SimpleDateFormat("EEEE, dd MMM yyyy HH:mm", Locale.getDefault())

        fun bind(data: ArticlesItem) {
            val date = dateFormat.parse(data.publishedAt)

            binding.apply {
                Glide.with(imgNews)
                    .load(data.urlToImage)
                    .error(R.drawable.ic_launcher_background)
                    .into(imgNews)

                tvJudul.text = data.title
                tvPenulis.text = data.author

                tvTanggalPosting.text = formatter.format(date).toString()
                binding.cardNews.setOnClickListener {
                    val intent = Intent(itemView.context, DetailNewsActivity::class.java)
                    intent.putExtra(DetailNewsActivity.EXTRA_NEWS,data)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rowBinding = ItemRowNewsBinding.inflate(layoutInflater, parent, false)
        return NewsViewHolder(rowBinding)
    }

    override fun getItemCount(): Int {
        return dataNews.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(dataNews[position])
    }

    fun setData(data: List<ArticlesItem> ){
        dataNews = data
        notifyDataSetChanged()
    }
}