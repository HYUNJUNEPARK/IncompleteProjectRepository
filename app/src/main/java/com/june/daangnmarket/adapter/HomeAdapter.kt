package com.june.daangnmarket.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.firebase.storage.StorageReference
import com.june.daangnmarket.R
import com.june.daangnmarket.activity.ArticleDetailActivity
import com.june.daangnmarket.databinding.ItemAriticleBinding
import com.june.daangnmarket.model.ArticleModel
import com.june.daangnmarket.key.DBKey.Companion.TAG
import com.june.daangnmarket.key.FirebaseVar.Companion.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Date
import java.text.SimpleDateFormat

class HomeAdapter(val fragmentContext: Context) : ListAdapter<ArticleModel, HomeAdapter.ViewHolder> (diffUtil) {
    inner class ViewHolder(private val binding: ItemAriticleBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(articleModel: ArticleModel) {
            val format = SimpleDateFormat("MM월 dd일")
            val date = Date(articleModel.createdAt!!.toLong())
            binding.titleTextView.text = articleModel.title
            binding.dateTextView.text = format.format(date).toString()
            binding.priceTextView.text = articleModel.price

            binding.root.setOnClickListener {
                val context = binding.thumbnailImageView.context
                val intent = Intent(context, ArticleDetailActivity::class.java)
                intent.putExtra("model", articleModel)
                context.startActivity(intent)
            }
            loadImgFromStorageAndSetByGlide(articleModel)
        }

        private fun loadImgFromStorageAndSetByGlide(articleModel: ArticleModel) {
            val storageRef = storage.reference
            val imgRef: StorageReference = storageRef.child("images_daangn/${articleModel.imageUrl}.jpg")

            CoroutineScope(Dispatchers.IO).launch {
                imgRef.downloadUrl
                    .addOnSuccessListener { uri ->
                        Glide.with(fragmentContext)
                            .load(uri)
                            .transform(CenterCrop(), RoundedCorners(18))
                            .error(R.drawable.ic_baseline_cancel_24)
                            .into(binding.thumbnailImageView)
                        binding.progressBar.visibility = View.INVISIBLE
                    }
                    .addOnFailureListener { e ->
                        Log.d(TAG, "addOnFailureListener Error: $e")
                    }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAriticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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