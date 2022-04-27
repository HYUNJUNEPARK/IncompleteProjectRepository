package com.june.daangnmarket.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.june.daangnmarket.R
import com.june.daangnmarket.adapter.ChatListAdapter
import com.june.daangnmarket.databinding.FragmentChatListBinding

class ChatListFragment : BaseFragment() {
    private var _binding: FragmentChatListBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var chatListAdapter: ChatListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChatListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatListAdapter = ChatListAdapter()

        binding.chatListRecyclerView.adapter = chatListAdapter
        binding.chatListRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}