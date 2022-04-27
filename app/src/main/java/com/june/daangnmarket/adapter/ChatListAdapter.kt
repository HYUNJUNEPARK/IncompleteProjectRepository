package com.june.daangnmarket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.june.daangnmarket.databinding.ItemChatlistBinding
import com.june.daangnmarket.model.ChatListItemModel

class ChatListAdapter(val onItemClicked: (ChatListItemModel) -> Unit) : ListAdapter<ChatListItemModel, ChatListAdapter.ViewHolder> (diffUtil) {
    inner class ViewHolder(private val binding: ItemChatlistBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(chatListItemModel: ChatListItemModel) {

            binding.root.setOnClickListener {
                onItemClicked(chatListItemModel)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChatlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ChatListItemModel>() {
            override fun areItemsTheSame(oldItem: ChatListItemModel, newItem: ChatListItemModel): Boolean {
                return oldItem.createdAt == newItem.createdAt
            }
            override fun areContentsTheSame(oldItem: ChatListItemModel, newItem: ChatListItemModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}