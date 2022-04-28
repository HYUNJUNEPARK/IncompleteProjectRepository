package com.june.daangnmarket.adapter

import android.content.Context
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
import com.june.daangnmarket.databinding.ItemChatlistBinding
import com.june.daangnmarket.key.DBKey.Companion.TAG
import com.june.daangnmarket.key.FirebaseVar.Companion.storage
import com.june.daangnmarket.model.ChatListModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatListAdapter(val onItemClicked: (ChatListModel) -> Unit, val context: Context) : ListAdapter<ChatListModel, ChatListAdapter.MyHolder> (diffUtil) {
    inner class MyHolder(private val binding: ItemChatlistBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(chatListModel: ChatListModel) {
            binding.root.setOnClickListener {
                onItemClicked(chatListModel)
            }
            binding.chatRoomTitleTextView.text = chatListModel.itemTitle
            binding.sellerId.text = chatListModel.sellerId

            loadImgFromStorageAndSetByGlide(chatListModel)
        }
        private fun loadImgFromStorageAndSetByGlide(chatListModel: ChatListModel) {
            val storageRef = storage.reference
            val imgRef: StorageReference = storageRef.child("images_daangn/${chatListModel.imageUrl}.jpg")

            CoroutineScope(Dispatchers.IO).launch {
                imgRef.downloadUrl
                    .addOnSuccessListener { uri ->
                        Glide.with(context)
                            .load(uri)
                            .transform(CenterCrop(), RoundedCorners(18))
                            .error(R.drawable.ic_baseline_cancel_24)
                            .into(binding.productImageView)
                        binding.imgProgressBar.visibility = View.INVISIBLE
                    }
                    .addOnFailureListener { e ->
                        Log.d(TAG, "addOnFailureListener Error: $e")
                        binding.imgProgressBar.visibility = View.INVISIBLE
                    }
            }









        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = ItemChatlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ChatListModel>() {
            override fun areItemsTheSame(oldItem: ChatListModel, newItem: ChatListModel): Boolean {
                return oldItem.key == newItem.key
            }
            override fun areContentsTheSame(oldItem: ChatListModel, newItem: ChatListModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}