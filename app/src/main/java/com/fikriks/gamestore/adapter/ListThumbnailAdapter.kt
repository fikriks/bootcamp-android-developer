package com.fikriks.gamestore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fikriks.gamestore.data.Thumbnail
import com.fikriks.gamestore.databinding.ItemListGameBinding

class ListThumbnailAdapter(private val listThumbnail: ArrayList<Thumbnail>): RecyclerView.Adapter<ListThumbnailAdapter.ListViewHolder>() {
    inner class ListViewHolder(val binding: ItemListGameBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(ItemListGameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return listThumbnail.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (photo) = listThumbnail[position]

        holder.binding.imgItemPhoto.setImageResource(photo)
    }
}