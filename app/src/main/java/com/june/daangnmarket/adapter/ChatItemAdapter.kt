package com.june.daangnmarket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.june.daangnmarket.databinding.ItemChatBinding
import com.june.daangnmarket.model.ChatItemModel

class ChatItemAdapter : ListAdapter<ChatItemModel, ChatItemAdapter.MyHolder> (diffUtil) {
    inner class MyHolder(private val binding: ItemChatBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(chatItem: ChatItemModel) {
            binding.senderTextView.text = chatItem.senderId
            binding.messageTextView.text = chatItem.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ChatItemModel>() {
            override fun areItemsTheSame(oldItem: ChatItemModel, newItem: ChatItemModel): Boolean {
                //TODO 고유값
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ChatItemModel, newItem: ChatItemModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}