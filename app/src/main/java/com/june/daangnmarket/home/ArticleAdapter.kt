package com.june.daangnmarket.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.firebase.storage.StorageReference
import com.june.daangnmarket.activity.ArticleDetailActivity
import com.june.daangnmarket.databinding.ItemAriticleBinding
import com.june.daangnmarket.share.DBKey.Companion.TAG
import com.june.daangnmarket.share.FirebaseVar.Companion.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Date
import java.text.SimpleDateFormat

class ArticleAdapter : ListAdapter<ArticleModel, ArticleAdapter.ViewHolder> (diffUtil) {
    inner class ViewHolder(private val binding: ItemAriticleBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(articleModel: ArticleModel) {
            val format = SimpleDateFormat("MM월 dd일")
            val date = Date(articleModel.createdAt!!.toLong())

            binding.root.setOnClickListener {
                val context = binding.thumbnailImageView.context
                val intent = Intent(context, ArticleDetailActivity::class.java)

                intent.putExtra("model", articleModel)
                context.startActivity(intent)
            }

            binding.titleTextView.text = articleModel.title
            binding.dateTextView.text = format.format(date).toString()
            binding.priceTextView.text = articleModel.price

            val storageRef = storage.reference
            val imgRef: StorageReference = storageRef.child("images_daangn/${articleModel.imageUri}.jpg")
            CoroutineScope(Dispatchers.IO).launch {
                imgRef.downloadUrl
                    .addOnSuccessListener { uri ->
                        Glide.with(binding.thumbnailImageView)
                            .load(uri)
                            .transform(CenterCrop(), RoundedCorners(18))
                            .into(binding.thumbnailImageView)
                        binding.progressBar.visibility = View.INVISIBLE
                    }
                    .addOnFailureListener { e ->
                        Log.d(TAG, "Error: $e")
                    }
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