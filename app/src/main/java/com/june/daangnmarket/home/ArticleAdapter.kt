package com.june.daangnmarket.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.june.daangnmarket.databinding.ItemAriticleBinding
import java.sql.Date
import java.text.SimpleDateFormat

class ArticleAdapter: ListAdapter<ArticleModel, ArticleAdapter.ViewHolder> (diffUtil) {
    inner class ViewHolder(private val binding: ItemAriticleBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(articleModel: ArticleModel) {
            val format = SimpleDateFormat("MM월 dd일")
            val date = Date(articleModel.createdAt!!.toLong())

            binding.titleTextView.text = articleModel.title
            binding.dateTextView.text = format.format(date).toString()
            binding.priceTextView.text = articleModel.price

            if (articleModel.imageUrl!!.isNotEmpty()) {
                Glide.with(binding.thumbnailImageView)
                    .load(articleModel.imageUrl)
                    .centerCrop()
                    .into(binding.thumbnailImageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleAdapter.ViewHolder {
        val binding = ItemAriticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleAdapter.ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ArticleModel>() {
            override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
                return oldItem.createdAt == newItem.createdAt
            }
            override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}